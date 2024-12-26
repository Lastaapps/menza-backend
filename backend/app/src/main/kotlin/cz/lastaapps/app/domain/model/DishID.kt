package cz.lastaapps.app.domain.model

import arrow.core.left
import arrow.core.right
import cz.lastaapps.base.Outcome
import cz.lastaapps.base.error.RatingError

@JvmInline
value class DishID private constructor(val id: String) {
    companion object {
        private const val MAX_ID_LENGTH = 32
        fun from(id: String): Outcome<DishID> = run {
            if (id.length >= MAX_ID_LENGTH || id.isBlank()) {
                RatingError.InvalidDishID().left()
            }

            DishID(id).right()
        }
    }
}
