package cz.lastaapps.app.data

import cz.lastaapps.app.domain.RatingRepository
import cz.lastaapps.app.domain.SerializationCache
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SerializationCacheImpl(
    private val repo: RatingRepository,
    private val scope: CoroutineScope,
) : SerializationCache {

    private val state by lazy {
        repo.getState()
            .map { Json.encodeToString(it) }
            .stateIn(scope, SharingStarted.Eagerly, Json.encodeToString(emptyList<Unit>()))
    }

    override fun getState(): Flow<String> = state
}
