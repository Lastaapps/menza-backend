package cz.lastaapps.app.data

import cz.lastaapps.app.config.ServerConfig
import cz.lastaapps.app.domain.RatingRepository
import cz.lastaapps.app.domain.model.DishProgress
import cz.lastaapps.app.domain.model.DishStatus
import cz.lastaapps.base.Result
import cz.lastaapps.base.error.RatingError
import cz.lastaapps.base.toResult
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class RatingRepositoryImpl(
    private val config: ServerConfig,
) : RatingRepository {
    private val map = MutableStateFlow(persistentMapOf<String, DishProgress>())
    private val value get() = map.value

    private var requestCounter = 0
    private val mutex = Mutex()

    override suspend fun resetRepository() {
        map.update { it.clear() }
    }

    override suspend fun rate(
        id: String,
        rating: UInt,
    ): Result<Unit> {
        when (val res = validateId(id)) {
            is Result.Error -> return res
            is Result.Success -> {}
        }

        if (rating !in 1u..5u) {
            return RatingError.RatingInvalidRange().toResult()
        }

        map.update {
            it.put(
                id,
                it.getOrDefault(id, DishProgress.default).run {
                    copy(ratingSum = ratingSum + rating, rateCount = rateCount + 1u)
                },
            )
        }

        incRequests()

        return Unit.toResult()
    }

    override suspend fun soldOut(id: String): Result<Unit> {
        when (val res = validateId(id)) {
            is Result.Error -> return res
            is Result.Success -> {}
        }
        map.update {
            it.put(
                id,
                it.getOrDefault(id, DishProgress.default).run {
                    copy(soldOutCount = soldOutCount + 1u)
                },
            )
        }

        incRequests()

        return Unit.toResult()
    }

    private fun validateId(id: String): Result<Unit> {
        if (id.length != 8) {
            return RatingError.InvalidIdLength().toResult()
        }

        if (config.maxPerDay < requestCounter) {
            return RatingError.RequestQuotaReached().toResult()
        }

        if (!value.containsKey(id) && value.keys.size > config.maxDishes) {
            return RatingError.DishQuotaReached().toResult()
        }

        return Unit.toResult()
    }

    private suspend fun incRequests() = mutex.withLock { requestCounter++ }

    override fun getState(): Flow<List<DishStatus>> =
        map.map { map ->
            map.map { (id, progress) ->
                with(progress) {
                    DishStatus(
                        id,
                        rateCount = rateCount,
                        ratingSum = ratingSum,
                        rating = rateCount.takeIf { it > 0u }?.let { ratingSum.toFloat() / it.toInt() },
                        soldOutCount = soldOutCount,
                    )
                }
            }
        }
}
