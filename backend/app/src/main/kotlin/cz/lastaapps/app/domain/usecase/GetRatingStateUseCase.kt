package cz.lastaapps.app.domain.usecase

import cz.lastaapps.app.data.RatingRepository
import cz.lastaapps.app.data.StatisticsRepository
import cz.lastaapps.app.domain.model.DishStatus
import cz.lastaapps.app.domain.model.MenzaID
import cz.lastaapps.base.usecase.UCParam
import cz.lastaapps.base.usecase.UseCase
import cz.lastaapps.base.usecase.UseCaseImpl
import kotlinx.coroutines.flow.first

interface GetRatingStateUseCase : UseCase<GetRatingStateUseCase.Param, List<DishStatus>> {
    @JvmInline
    value class Param(val id: MenzaID) : UCParam
}

class GetRatingStateUseCaseImpl(
    private val repo: RatingRepository,
    private val statistics: StatisticsRepository,
) : GetRatingStateUseCase, UseCaseImpl<GetRatingStateUseCase.Param, List<DishStatus>>() {

    override suspend fun doWork(params: GetRatingStateUseCase.Param): List<DishStatus> =
        repo.getState(menzaID = params.id).first()
            .also { statistics.incStatus() }
}
