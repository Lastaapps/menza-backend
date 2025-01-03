package cz.lastaapps.app.data.impl

import cz.lastaapps.app.data.StatisticsRepository
import cz.lastaapps.app.domain.model.RepoStatistics
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

internal class StatisticsRepositoryImpl : StatisticsRepository {
    private var statistics = RepoStatistics()
    private val mutex = Mutex()

    private suspend inline fun update(block: RepoStatistics.() -> RepoStatistics) {
        mutex.withLock { statistics = statistics.block() }
    }

    override suspend fun incRating() = update { copy(ratings = ratings + 1u) }

    override suspend fun incStatistics() = update { copy(statistics = statistics + 1u) }

    override suspend fun incStatus() = update { copy(state = state + 1u) }

    override suspend fun reset() = update { RepoStatistics() }

    override suspend fun getState(): RepoStatistics = mutex.withLock { statistics }
}
