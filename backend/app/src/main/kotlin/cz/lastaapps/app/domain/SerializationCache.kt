package cz.lastaapps.app.domain

import cz.lastaapps.app.domain.model.DishStatus

interface SerializationCache {
    suspend fun cache(data: List<DishStatus>): String
}