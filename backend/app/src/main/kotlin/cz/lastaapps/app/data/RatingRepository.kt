package cz.lastaapps.app.data

import cz.lastaapps.app.domain.model.DishStatus
import cz.lastaapps.app.domain.model.MenzaID
import cz.lastaapps.app.domain.model.RatingRequest
import cz.lastaapps.base.Outcome
import kotlinx.coroutines.flow.Flow

interface RatingRepository {
    suspend fun resetRepository()

    suspend fun rate(
        request: RatingRequest,
    ): Outcome<Unit>

    suspend fun getState(menzaID: MenzaID): Flow<List<DishStatus>>
}
