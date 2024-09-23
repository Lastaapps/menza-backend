package cz.lastaapps.app.domain.usecase

import arrow.core.Either
import cz.lastaapps.app.domain.RatingRepository
import cz.lastaapps.app.domain.StatisticsRepository
import cz.lastaapps.base.Outcome
import cz.lastaapps.base.usecase.UCParam
import cz.lastaapps.base.usecase.UseCaseOutcome
import cz.lastaapps.base.usecase.UseCaseOutcomeImpl

interface RateUseCase : UseCaseOutcome<RateUseCase.Params, Unit> {
    data class Params(
        val id: String,
        val rating: UInt,
    ) : UCParam
}

internal class RateUseCaseImpl(
    private val repo: RatingRepository,
    private val statistics: StatisticsRepository,
) : UseCaseOutcomeImpl<RateUseCase.Params, Unit>(),
    RateUseCase {
    override suspend fun doWork(params: RateUseCase.Params): Outcome<Unit> =
        repo.rate(params.id, params.rating).also {
            if (it is Either.Right) statistics.incRating(params.rating)
        }
}
