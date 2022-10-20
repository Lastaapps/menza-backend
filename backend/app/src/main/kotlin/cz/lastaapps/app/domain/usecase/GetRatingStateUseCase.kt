package cz.lastaapps.app.domain.usecase

import cz.lastaapps.app.domain.StatisticsRepository
import cz.lastaapps.base.usecase.UseCaseNoParams
import cz.lastaapps.base.usecase.UseCaseNoParamsImpl

interface GetRatingStateUseCase : UseCaseNoParams<String>

class GetRatingStateUseCaseImpl(
    private val statistics: StatisticsRepository,
    private val cached: CacheStateUseCase
) : GetRatingStateUseCase, UseCaseNoParamsImpl<String>() {
    override suspend fun doWork(): String =
        cached().also { statistics.incStatus() }
}