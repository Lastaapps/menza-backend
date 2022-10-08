package cz.lastaapps.app

import cz.lastaapps.app.data.ClearCronImpl
import cz.lastaapps.app.data.RatingRepositoryImpl
import cz.lastaapps.app.data.SerializationCacheImpl
import cz.lastaapps.app.domain.ClearCron
import cz.lastaapps.app.domain.RatingRepository
import cz.lastaapps.app.domain.SerializationCache
import cz.lastaapps.app.domain.usecase.*
import cz.lastaapps.app.presentation.Routes
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val appModule = module {
    singleOf(::RatingRepositoryImpl) { bind<RatingRepository>() }
    singleOf(::ClearCronImpl) { bind<ClearCron>() }
    singleOf(::SerializationCacheImpl) { bind<SerializationCache>() }
    singleOf(::Routes)

    factoryOf(::GetRatingStateUseCaseImpl) { bind<GetRatingStateUseCase>() }
    factoryOf(::RateUseCaseImpl) { bind<RateUseCase>() }
    factoryOf(::SoldOutUseCaseImpl) { bind<SoldOutUseCase>() }
}