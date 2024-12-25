package cz.lastaapps.app.domain.usecase

import cz.lastaapps.app.data.RatingRepository
import cz.lastaapps.app.data.StatisticsRepository
import cz.lastaapps.app.domain.model.DishStatus
import cz.lastaapps.app.domain.model.RatingRequest
import cz.lastaapps.base.Outcome
import cz.lastaapps.base.usecase.UCParam
import cz.lastaapps.base.usecase.UseCaseOutcome
import cz.lastaapps.base.usecase.UseCaseOutcomeImpl
import kotlinx.coroutines.flow.first

interface RateUseCase : UseCaseOutcome<RateUseCase.Params, List<DishStatus>> {
    data class Params(
        val ratingRequest: RatingRequest,
    ) : UCParam
}

internal class RateUseCaseImpl(
    private val repo: RatingRepository,
    private val statistics: StatisticsRepository,
) : UseCaseOutcomeImpl<RateUseCase.Params, List<DishStatus>>(),
    RateUseCase {
    override suspend fun doWork(params: RateUseCase.Params): Outcome<List<DishStatus>> =
        repo.rate(params.ratingRequest).onRight {
            statistics.incRating()
        }.map { repo.getState(params.ratingRequest.menzaID).first() }
}
