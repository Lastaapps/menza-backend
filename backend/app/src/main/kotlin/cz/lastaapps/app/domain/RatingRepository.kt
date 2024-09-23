package cz.lastaapps.app.domain

import cz.lastaapps.app.domain.model.DishStatus
import cz.lastaapps.base.Result
import kotlinx.coroutines.flow.Flow

interface RatingRepository {
    suspend fun resetRepository()

    suspend fun rate(
        id: String,
        rating: UInt,
    ): Result<Unit>

    suspend fun soldOut(id: String): Result<Unit>

    fun getState(): Flow<List<DishStatus>>
}
