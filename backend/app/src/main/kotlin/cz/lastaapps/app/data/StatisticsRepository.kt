package cz.lastaapps.app.data

import cz.lastaapps.app.domain.model.RepoStatistics

interface StatisticsRepository {
    suspend fun incRating()

    suspend fun incStatistics()

    suspend fun incStatus()

    suspend fun reset()

    suspend fun getState(): RepoStatistics
}
