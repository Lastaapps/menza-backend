package cz.lastaapps.app.domain.model

import arrow.core.left
import arrow.core.right
import cz.lastaapps.base.Outcome
import cz.lastaapps.base.error.RatingError

@JvmInline
value class MenzaID private constructor(val name: String) {
    companion object {
        fun fromString(name: String): Outcome<MenzaID> = run {
            if (name.isBlank() || name.length > 32) {
                return RatingError.InvalidMenzaID().left()
            } else {
                MenzaID(name = name).right()
            }
        }
    }
}

