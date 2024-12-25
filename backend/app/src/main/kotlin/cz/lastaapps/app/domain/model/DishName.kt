package cz.lastaapps.app.domain.model

import arrow.core.left
import arrow.core.right
import cz.lastaapps.base.Outcome
import cz.lastaapps.base.error.RatingError

class DishName private constructor(
    val id: String,
    val name: String,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is DishName) return false

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    companion object {
        fun fromString(name: String): Outcome<DishName> = run {
            val id = name.filter { it.isLetterOrDigit() }.lowercase()
            if (id.isBlank() || name.length > 256) {
                return RatingError.InvalidDishName().left()
            } else {
                DishName(name = name, id = id).right()
            }
        }
    }
}
