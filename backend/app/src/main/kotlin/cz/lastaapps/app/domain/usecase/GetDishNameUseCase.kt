package cz.lastaapps.app.domain.usecase

import arrow.core.left
import arrow.core.raise.either
import cz.lastaapps.app.data.DishNameRepository
import cz.lastaapps.app.domain.model.DishDescriptor
import cz.lastaapps.app.domain.model.DishID
import cz.lastaapps.app.domain.model.DishName
import cz.lastaapps.app.domain.model.MenzaID
import cz.lastaapps.base.Outcome
import cz.lastaapps.base.error.RatingError
import cz.lastaapps.base.usecase.UCParam
import cz.lastaapps.base.usecase.UseCase
import cz.lastaapps.base.usecase.UseCaseImpl

interface GetDishNameUseCase : UseCase<GetDishNameUseCase.Params, Outcome<DishDescriptor>> {
    data class Params(
        val menzaID: MenzaID,
        val dishID: DishID?,
        val nameCs: DishName?,
        val nameEn: DishName?,
    ) : UCParam
}

class GetDishNameUseCaseImpl(
    private val repo: DishNameRepository,
) : GetDishNameUseCase, UseCaseImpl<GetDishNameUseCase.Params, Outcome<DishDescriptor>>() {

    override suspend fun doWork(params: GetDishNameUseCase.Params): Outcome<DishDescriptor> = either {
        params.dishID?.let {
            return repo.getForId(
                params.menzaID,
                params.dishID,
                newDishNameCs = params.nameCs,
                newDishNameEn = params.nameEn,
            )
        }

        params.nameCs?.let {
            return repo.getForNameCs(params.menzaID, params.nameCs)
        }
        params.nameEn?.let {
            return repo.getForNameEn(params.menzaID, params.nameEn)
        }

        return RatingError.NoDishIdNorNameDefined().left()
    }
}
