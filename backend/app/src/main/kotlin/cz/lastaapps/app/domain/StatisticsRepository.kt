package cz.lastaapps.app.domain

import cz.lastaapps.app.domain.model.RepoStatistics

interface StatisticsRepository {
    suspend fun incRating(rating: UInt)

    suspend fun incSoldOut()

    suspend fun incStatistics()

    suspend fun incStatus()

    suspend fun reset()

    suspend fun getState(): RepoStatistics
}
