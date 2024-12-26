package cz.lastaapps.app.data.impl

import arrow.core.left
import arrow.core.raise.either
import arrow.core.right
import cz.lastaapps.app.config.ServerConfig
import cz.lastaapps.app.data.RatingRepository
import cz.lastaapps.app.domain.model.DishID
import cz.lastaapps.app.domain.model.DishStatus
import cz.lastaapps.app.domain.model.DishStatusList
import cz.lastaapps.app.domain.model.MenzaID
import cz.lastaapps.app.domain.model.RatingGroup
import cz.lastaapps.app.domain.model.RatingKinds
import cz.lastaapps.app.domain.model.RatingRequest
import cz.lastaapps.base.Outcome
import cz.lastaapps.base.error.RatingError
import kotlin.time.Duration.Companion.seconds
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

private typealias MapItem = PersistentMap<DishID, RatingKinds<RatingGroup>>

class RatingRepositoryImpl(
    private val config: ServerConfig,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default),
    private val clock: Clock = Clock.System,
) : RatingRepository {

    private var map =
        persistentMapOf<MenzaID, MutableStateFlow<Pair<Instant, MapItem>>>()
    private var outerMap =
        persistentMapOf<MenzaID, SharedFlow<Pair<Instant, DishStatusList>>>()

    private var requestCounter = 0
    private val mutex = Mutex()

    override suspend fun resetRepository() {
        mutex.withLock { map = persistentMapOf() }
    }

    private suspend fun getMap(menzaID: MenzaID): Outcome<Pair<MutableStateFlow<Pair<Instant, MapItem>>, SharedFlow<Pair<Instant, DishStatusList>>>> {
        mutex.withLock {
            if (!map.containsKey(menzaID)) {
                if (map.size >= config.maxMenzas) {
                    return RatingError.MenzaQuotaReached().left()
                }

                val flow = MutableStateFlow(clock.now() to persistentMapOf<DishID, RatingKinds<RatingGroup>>())
                map = map.put(menzaID, flow)
                val mappedFlow = flow
                    .map { (timeStamp, data) -> timeStamp to data.mapToDishState() }
                    .shareIn(scope, SharingStarted.WhileSubscribed(60.seconds), replay = 1)
                outerMap = outerMap.put(menzaID, mappedFlow)
            }
        }
        return (map[menzaID]!! to outerMap[menzaID]!!).right()
    }

    override suspend fun rate(
        request: RatingRequest,
    ): Outcome<Unit> = either {
        var instant: Instant = Instant.DISTANT_FUTURE
        // I don't care about throughput in this project
        val (map, outerMap) = getMap(request.menzaID).bind()
        map.update { (_, dishMap) ->
            if (dishMap.size >= config.maxDishes) {
                return RatingError.DishQuotaReached().left()
            }
            dishMap[request.dishId]?.let {
                val audience = maxOf(it.taste.audience, it.portion.audience, it.worthiness.audience)
                if (audience.toInt() >= config.maxPerDay) {
                    return RatingError.RequestQuotaReached().left()
                }
            }

            instant = clock.now()
            instant to dishMap.put(
                request.dishId,
                dishMap.getOrDefault(request.dishId, RatingGroup.defaultKinds).let { ratings ->
                    RatingKinds(
                        ratings.taste + request.kinds.taste,
                        ratings.portion + request.kinds.portion,
                        ratings.worthiness + request.kinds.worthiness,
                    )
                },
            )
        }
        outerMap.first { it.first >= instant }

        incRequests()

        return Unit.right()
    }

    private suspend fun incRequests() = mutex.withLock { requestCounter++ }

    private fun PersistentMap<DishID, RatingKinds<RatingGroup>>.mapToDishState() =
        map { (id, progress) ->
            DishStatus(
                dishID = id,
                categories = progress,
            )
        }

    override suspend fun getState(menzaID: MenzaID): Flow<List<DishStatus>> = run {
        getMap(menzaID).fold(
            { flow { emit(emptyList()) } },
            { map -> map.second.map { it.second } },
        )
    }
}
