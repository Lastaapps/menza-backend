package cz.lastaapps.app.domain.usecase

import cz.lastaapps.app.domain.StatisticsRepository
import cz.lastaapps.app.domain.model.RepoStatistics
import cz.lastaapps.base.usecase.UseCaseNoParams
import cz.lastaapps.base.usecase.UseCaseNoParamsImpl

interface GetStatisticsUseCase : UseCaseNoParams<RepoStatistics>

class GetStatisticsUseCaseImpl(
    private val statistics: StatisticsRepository,
) : GetStatisticsUseCase, UseCaseNoParamsImpl<RepoStatistics>() {
    override suspend fun doWork(): RepoStatistics {
        statistics.incStatistics()
        return statistics.getState()
    }
}
