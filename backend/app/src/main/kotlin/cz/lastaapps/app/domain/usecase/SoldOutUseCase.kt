package cz.lastaapps.app.domain.usecase

import arrow.core.Either
import cz.lastaapps.app.domain.RatingRepository
import cz.lastaapps.app.domain.StatisticsRepository
import cz.lastaapps.base.Outcome
import cz.lastaapps.base.usecase.UCParam
import cz.lastaapps.base.usecase.UseCaseOutcome
import cz.lastaapps.base.usecase.UseCaseOutcomeImpl

interface SoldOutUseCase : UseCaseOutcome<SoldOutUseCase.Params, Unit> {
    data class Params(
        val id: String,
    ) : UCParam
}

internal class SoldOutUseCaseImpl(
    private val repo: RatingRepository,
    private val statistics: StatisticsRepository,
) : UseCaseOutcomeImpl<SoldOutUseCase.Params, Unit>(),
    SoldOutUseCase {
    override suspend fun doWork(params: SoldOutUseCase.Params): Outcome<Unit> =
        repo.soldOut(params.id).also {
            if (it is Either.Right) statistics.incSoldOut()
        }
}
