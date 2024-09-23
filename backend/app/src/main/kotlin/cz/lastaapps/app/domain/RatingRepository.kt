package cz.lastaapps.app.domain

import cz.lastaapps.app.domain.model.DishStatus
import cz.lastaapps.base.Outcome
import kotlinx.coroutines.flow.Flow

interface RatingRepository {
    suspend fun resetRepository()

    suspend fun rate(
        id: String,
        rating: UInt,
    ): Outcome<Unit>

    suspend fun soldOut(id: String): Outcome<Unit>

    fun getState(): Flow<List<DishStatus>>
}
