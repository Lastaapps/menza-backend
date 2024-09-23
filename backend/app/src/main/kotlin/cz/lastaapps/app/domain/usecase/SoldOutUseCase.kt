package cz.lastaapps.app.domain.usecase

import cz.lastaapps.app.domain.RatingRepository
import cz.lastaapps.app.domain.StatisticsRepository
import cz.lastaapps.base.Result
import cz.lastaapps.base.usecase.UCParam
import cz.lastaapps.base.usecase.UseCaseResult
import cz.lastaapps.base.usecase.UseCaseResultImpl

interface SoldOutUseCase : UseCaseResult<SoldOutUseCase.Params, Unit> {
    data class Params(val id: String) : UCParam
}

internal class SoldOutUseCaseImpl(
    private val repo: RatingRepository,
    private val statistics: StatisticsRepository,
) : SoldOutUseCase, UseCaseResultImpl<SoldOutUseCase.Params, Unit>() {
    override suspend fun doWork(params: SoldOutUseCase.Params): Result<Unit> =
        repo.soldOut(params.id).also {
            if (it is Result.Success) statistics.incSoldOut()
        }
}
