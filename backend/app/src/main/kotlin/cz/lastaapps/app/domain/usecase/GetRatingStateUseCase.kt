package cz.lastaapps.app.domain.usecase

import cz.lastaapps.app.domain.SerializationCache
import cz.lastaapps.base.usecase.UseCaseNoParams
import cz.lastaapps.base.usecase.UseCaseNoParamsImpl
import kotlinx.coroutines.flow.first

interface GetRatingStateUseCase : UseCaseNoParams<String>

class GetRatingStateUseCaseImpl(
    private val cache: SerializationCache,
) : GetRatingStateUseCase, UseCaseNoParamsImpl<String>() {
    override suspend fun doWork(): String = cache.getState().first()
}