package cz.lastaapps.app.data.impl

import arrow.core.left
import arrow.core.right
import cz.lastaapps.app.data.DishNameRepository
import cz.lastaapps.app.domain.model.DishDescriptor
import cz.lastaapps.app.domain.model.DishID
import cz.lastaapps.app.domain.model.DishName
import cz.lastaapps.app.domain.model.MenzaID
import cz.lastaapps.base.Outcome
import cz.lastaapps.base.error.RatingError
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class DishNameRepositoryImpl : DishNameRepository {
    private data class RepoState(
        val map: MutableMap<DishID, DishDescriptor> = mutableMapOf(),
        val mapNameCsToId: MutableMap<DishName, DishID> = mutableMapOf(),
        val mapNameEnToId: MutableMap<DishName, DishID> = mutableMapOf(),
    )

    private val statesMap = mutableMapOf<MenzaID, RepoState>()

    private val mutex = Mutex()

    override suspend fun resetRepository() = mutex.withLock {
        statesMap.clear()
    }

    private fun getState(menzaID: MenzaID) =
        statesMap.getOrPut(menzaID) { RepoState() }

    override suspend fun getForId(
        menzaID: MenzaID,
        id: DishID,
        newDishNameCs: DishName?,
        newDishNameEn: DishName?,
    ): Outcome<DishDescriptor> = mutex.withLock {
        val state = getState(menzaID)
        if (state.map.containsKey(id)) {
            var current = state.map[id]!!

            var anyChanged = false
            if (newDishNameCs != null && current.nameCs != newDishNameCs) {
                // state.mapNameCsToId.remove(current.nameCs)
                state.mapNameCsToId[newDishNameCs] = id
                anyChanged = true
                current = current.copy(nameCs = newDishNameCs)
            }
            if (newDishNameEn != null && current.nameEn != newDishNameEn) {
                // state.mapNameEnToId.remove(current.nameEn)
                state.mapNameEnToId[newDishNameEn] = id
                anyChanged = true
                current = current.copy(nameEn = newDishNameEn)
            }
            if (anyChanged) {
                state.map[id] = current
            }

            return@withLock current.right()
        }

        if (newDishNameCs == null && newDishNameEn == null) {
            return@withLock RatingError.UnknownDishName().left()
        }
        val newItem = DishDescriptor(id, newDishNameCs, newDishNameEn)
        if (newDishNameCs != null) {
            state.mapNameCsToId[newDishNameCs] = id
        }
        if (newDishNameEn != null) {
            state.mapNameEnToId[newDishNameEn] = id
        }
        state.map[id] = newItem
        return@withLock newItem.right()
    }

    override suspend fun getForNameCs(menzaID: MenzaID, newDishNameCs: DishName?): Outcome<DishDescriptor> =
        mutex.withLock {
            val state = getState(menzaID)
            state.mapNameCsToId[newDishNameCs]?.let { id -> state.map[id]!! }?.right() ?: RatingError.UnknownDishName()
                .left()
        }

    override suspend fun getForNameEn(menzaID: MenzaID, newDishNameEn: DishName?): Outcome<DishDescriptor> =
        mutex.withLock {
            val state = getState(menzaID)
            state.mapNameEnToId[newDishNameEn]?.let { id -> state.map[id]!! }?.right() ?: RatingError.UnknownDishName()
                .left()
        }
}