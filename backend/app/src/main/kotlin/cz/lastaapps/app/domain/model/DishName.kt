package cz.lastaapps.app.domain.model

import arrow.core.left
import arrow.core.right
import cz.lastaapps.base.Outcome
import cz.lastaapps.base.error.RatingError
import java.util.prefs.Preferences.MAX_NAME_LENGTH

@JvmInline
value class DishName private constructor(val value: String) {
    companion object {
        fun from(name: String): Outcome<DishName> = run {
            if (name.length >= MAX_NAME_LENGTH || name.isBlank()) {
                RatingError.InvalidDishName().left()
            } else {
                DishName(name).right()
            }
        }
    }
}