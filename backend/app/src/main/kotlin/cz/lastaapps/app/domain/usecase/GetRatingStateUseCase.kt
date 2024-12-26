package cz.lastaapps.app.domain.usecase

import cz.lastaapps.app.data.DishNameRepository
import cz.lastaapps.app.data.RatingRepository
import cz.lastaapps.app.data.StatisticsRepository
import cz.lastaapps.app.domain.model.MenzaID
import cz.lastaapps.app.domain.model.NamedDishStatus
import cz.lastaapps.base.usecase.UCParam
import cz.lastaapps.base.usecase.UseCase
import cz.lastaapps.base.usecase.UseCaseImpl
import kotlinx.coroutines.flow.first

interface GetRatingStateUseCase : UseCase<GetRatingStateUseCase.Param, List<NamedDishStatus>> {
    @JvmInline
    value class Param(val id: MenzaID) : UCParam
}

class GetRatingStateUseCaseImpl(
    private val repo: RatingRepository,
    private val namesRepo: DishNameRepository,
    private val statistics: StatisticsRepository,
) : GetRatingStateUseCase, UseCaseImpl<GetRatingStateUseCase.Param, List<NamedDishStatus>>() {

    override suspend fun doWork(params: GetRatingStateUseCase.Param): List<NamedDishStatus> =
        repo.getState(menzaID = params.id).first()
            .map {
                NamedDishStatus(
                    dishDescriptor = namesRepo.getForId(params.id, it.dishID).getOrNull()!!,
                    categories = it.categories,
                )
            }
            .also { statistics.incStatus() }
}
