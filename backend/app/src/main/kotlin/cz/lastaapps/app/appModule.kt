package cz.lastaapps.app

import cz.lastaapps.app.data.ClearCron
import cz.lastaapps.app.data.DishNameRepository
import cz.lastaapps.app.data.RatingRepository
import cz.lastaapps.app.data.StatisticsRepository
import cz.lastaapps.app.data.impl.ClearCronImpl
import cz.lastaapps.app.data.impl.DishNameRepositoryImpl
import cz.lastaapps.app.data.impl.RatingRepositoryImpl
import cz.lastaapps.app.data.impl.StatisticsRepositoryImpl
import cz.lastaapps.app.domain.usecase.GetDishNameUseCase
import cz.lastaapps.app.domain.usecase.GetDishNameUseCaseImpl
import cz.lastaapps.app.domain.usecase.GetRatingStateUseCase
import cz.lastaapps.app.domain.usecase.GetRatingStateUseCaseImpl
import cz.lastaapps.app.domain.usecase.GetStatisticsUseCase
import cz.lastaapps.app.domain.usecase.GetStatisticsUseCaseImpl
import cz.lastaapps.app.domain.usecase.RateUseCase
import cz.lastaapps.app.domain.usecase.RateUseCaseImpl
import cz.lastaapps.app.presentation.Routes
import kotlinx.datetime.Clock
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal val appModule =
    module {
        single { Clock.System } bind Clock::class
        single<RatingRepository> { RatingRepositoryImpl(get()) }
        singleOf(::ClearCronImpl) { bind<ClearCron>() }
        singleOf(::Routes)
        singleOf(::StatisticsRepositoryImpl) { bind<StatisticsRepository>() }
        singleOf(::DishNameRepositoryImpl) { bind<DishNameRepository>() }

        singleOf(::GetRatingStateUseCaseImpl) { bind<GetRatingStateUseCase>() }
        singleOf(::RateUseCaseImpl) { bind<RateUseCase>() }
        singleOf(::GetStatisticsUseCaseImpl) { bind<GetStatisticsUseCase>() }
        singleOf(::GetDishNameUseCaseImpl) { bind<GetDishNameUseCase>() }
    }
