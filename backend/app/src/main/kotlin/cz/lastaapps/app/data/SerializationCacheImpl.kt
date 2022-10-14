package cz.lastaapps.app.data

import cz.lastaapps.app.domain.SerializationCache
import cz.lastaapps.app.domain.model.DishStatus
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SerializationCacheImpl : SerializationCache {

    private val mutex = Mutex()
    private var cacheKey: List<DishStatus>? = null
    private var cacheValue = ""

    override suspend fun cache(data: List<DishStatus>): String = mutex.withLock {
        if (data == cacheKey) return cacheValue
        cacheKey = data
        Json.encodeToString(data).also { cacheValue = it }
    }
}
