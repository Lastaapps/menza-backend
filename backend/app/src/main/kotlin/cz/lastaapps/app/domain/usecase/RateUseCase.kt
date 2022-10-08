package cz.lastaapps.app.domain.usecase

import cz.lastaapps.app.domain.RatingRepository
import cz.lastaapps.base.Result
import cz.lastaapps.base.usecase.UCParam
import cz.lastaapps.base.usecase.UseCaseResult
import cz.lastaapps.base.usecase.UseCaseResultImpl

interface RateUseCase : UseCaseResult<RateUseCase.Params, Unit> {
    data class Params(val id: String, val rating: UInt) : UCParam
}

internal class RateUseCaseImpl(
    private val repo: RatingRepository,
) : RateUseCase, UseCaseResultImpl<RateUseCase.Params, Unit>() {
    override suspend fun doWork(params: RateUseCase.Params): Result<Unit> =
        repo.rate(params.id, params.rating)
}