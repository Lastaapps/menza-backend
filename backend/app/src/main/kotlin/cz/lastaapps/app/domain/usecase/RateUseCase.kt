package cz.lastaapps.app.domain.usecase

import cz.lastaapps.app.data.DishNameRepository
import cz.lastaapps.app.data.RatingRepository
import cz.lastaapps.app.data.StatisticsRepository
import cz.lastaapps.app.domain.model.NamedDishStatus
import cz.lastaapps.app.domain.model.RatingRequest
import cz.lastaapps.base.Outcome
import cz.lastaapps.base.usecase.UCParam
import cz.lastaapps.base.usecase.UseCaseOutcome
import cz.lastaapps.base.usecase.UseCaseOutcomeImpl
import kotlinx.coroutines.flow.first

interface RateUseCase : UseCaseOutcome<RateUseCase.Params, List<NamedDishStatus>> {
    data class Params(
        val ratingRequest: RatingRequest,
    ) : UCParam
}

internal class RateUseCaseImpl(
    private val repo: RatingRepository,
    private val namesRepo: DishNameRepository,
    private val statistics: StatisticsRepository,
) : UseCaseOutcomeImpl<RateUseCase.Params, List<NamedDishStatus>>(),
    RateUseCase {
    override suspend fun doWork(params: RateUseCase.Params): Outcome<List<NamedDishStatus>> =
        repo.rate(params.ratingRequest)
            .map {
                repo.getState(params.ratingRequest.menzaID).first()
                    .map {
                        NamedDishStatus(
                            dishDescriptor = namesRepo.getForId(params.ratingRequest.menzaID, it.dishID).getOrNull()!!,
                            categories = it.categories,
                        )
                    }
            }
            .onRight { statistics.incRating() }
}
