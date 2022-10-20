package cz.lastaapps.app

import cz.lastaapps.app.data.ClearCronImpl
import cz.lastaapps.app.data.RatingRepositoryImpl
import cz.lastaapps.app.data.SerializationCacheImpl
import cz.lastaapps.app.data.StatisticsRepositoryImpl
import cz.lastaapps.app.domain.ClearCron
import cz.lastaapps.app.domain.RatingRepository
import cz.lastaapps.app.domain.SerializationCache
import cz.lastaapps.app.domain.StatisticsRepository
import cz.lastaapps.app.domain.usecase.*
import cz.lastaapps.app.presentation.Routes
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val appModule = module {
    singleOf(::RatingRepositoryImpl) { bind<RatingRepository>() }
    singleOf(::ClearCronImpl) { bind<ClearCron>() }
    singleOf(::SerializationCacheImpl) { bind<SerializationCache>() }
    singleOf(::Routes)
    singleOf(::StatisticsRepositoryImpl) { bind<StatisticsRepository>() }

    singleOf(::GetRatingStateUseCaseImpl) { bind<GetRatingStateUseCase>() }
    singleOf(::RateUseCaseImpl) { bind<RateUseCase>() }
    singleOf(::SoldOutUseCaseImpl) { bind<SoldOutUseCase>() }
    singleOf(::GetStatisticsUseCaseImpl) { bind<GetStatisticsUseCase>() }
    singleOf(::CacheStateUseCaseImpl) { bind<CacheStateUseCase>() }
}