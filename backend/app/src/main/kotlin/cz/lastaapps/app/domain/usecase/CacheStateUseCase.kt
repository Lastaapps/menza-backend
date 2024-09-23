package cz.lastaapps.app.domain.usecase

import cz.lastaapps.app.domain.RatingRepository
import cz.lastaapps.app.domain.SerializationCache
import cz.lastaapps.base.usecase.UseCaseNoParams
import cz.lastaapps.base.usecase.UseCaseNoParamsImpl
import kotlinx.coroutines.flow.first

interface CacheStateUseCase : UseCaseNoParams<String>

internal class CacheStateUseCaseImpl(
    private val repo: RatingRepository,
    private val cache: SerializationCache,
) : CacheStateUseCase, UseCaseNoParamsImpl<String>() {
    override suspend fun doWork(): String = cache.cache(repo.getState().first())
}
