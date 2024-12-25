package cz.lastaapps.app.domain.model

import arrow.core.left
import arrow.core.right
import cz.lastaapps.base.Outcome
import cz.lastaapps.base.error.RatingError

/**
 * 0 means no rating
 */
@JvmInline
value class RatingValue private constructor(val value: UInt) {
    companion object {
        val empty = RatingValue(0u)

        fun from(value: UInt): Outcome<RatingValue> = run {
            if (value !in 1u..5u) {
                return RatingError.InvalidRatingValue().left()
            } else {
                RatingValue(value).right()
            }
        }
    }
}