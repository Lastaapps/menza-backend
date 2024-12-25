package cz.lastaapps.app

import cz.lastaapps.app.data.ClearCron
import cz.lastaapps.app.data.RatingRepository
import cz.lastaapps.app.data.StatisticsRepository
import cz.lastaapps.app.data.impl.ClearCronImpl
import cz.lastaapps.app.data.impl.RatingRepositoryImpl
import cz.lastaapps.app.data.impl.StatisticsRepositoryImpl
import cz.lastaapps.app.domain.usecase.GetRatingStateUseCase
import cz.lastaapps.app.domain.usecase.GetRatingStateUseCaseImpl
import cz.lastaapps.app.domain.usecase.GetStatisticsUseCase
import cz.lastaapps.app.domain.usecase.GetStatisticsUseCaseImpl
import cz.lastaapps.app.domain.usecase.RateUseCase
import cz.lastaapps.app.domain.usecase.RateUseCaseImpl
import cz.lastaapps.app.presentation.Routes
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val appModule =
    module {
        single<RatingRepository> { RatingRepositoryImpl(get()) }
        singleOf(::ClearCronImpl) { bind<ClearCron>() }
        singleOf(::Routes)
        singleOf(::StatisticsRepositoryImpl) { bind<StatisticsRepository>() }

        singleOf(::GetRatingStateUseCaseImpl) { bind<GetRatingStateUseCase>() }
        singleOf(::RateUseCaseImpl) { bind<RateUseCase>() }
        singleOf(::GetStatisticsUseCaseImpl) { bind<GetStatisticsUseCase>() }
    }
