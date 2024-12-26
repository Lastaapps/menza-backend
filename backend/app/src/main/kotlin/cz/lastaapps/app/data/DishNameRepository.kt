package cz.lastaapps.app.data

import cz.lastaapps.app.domain.model.DishDescriptor
import cz.lastaapps.app.domain.model.DishID
import cz.lastaapps.app.domain.model.DishName
import cz.lastaapps.app.domain.model.MenzaID
import cz.lastaapps.base.Outcome

interface DishNameRepository {

    suspend fun resetRepository()

    suspend fun getForId(
        menzaID: MenzaID,
        id: DishID,
        newDishNameCs: DishName? = null,
        newDishNameEn: DishName? = null,
    ): Outcome<DishDescriptor>

    suspend fun getForNameCs(menzaID: MenzaID, newDishNameCs: DishName?): Outcome<DishDescriptor>

    suspend fun getForNameEn(menzaID: MenzaID, newDishNameEn: DishName?): Outcome<DishDescriptor>
}
