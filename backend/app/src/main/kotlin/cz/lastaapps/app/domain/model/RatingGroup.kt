package cz.lastaapps.app.domain.model

import kotlin.math.max

/**
 * @param sum sum of all ratings (of multiple categories)
 * @param rating count of all ratings (of multiple categories)
 * @param audience count of all ratings, differs when this is combined
 */
data class RatingGroup(
    val sum: UInt,
    val count: UInt,
    val audience: UInt = count,
) {
    val average: Float
        get() = if (count == 0u) 0.0f else sum.toFloat() / count.toFloat()

    companion object {
        internal val default = RatingGroup(0u, 0u)
        internal val defaultKinds = RatingKinds(
            default, default, default,
        )
    }

    operator fun plus(other: RatingValue) =
        if (other == RatingValue.empty) {
            this
        } else {
            RatingGroup(
                sum = sum + other.value,
                count = count + 1u,
            )
        }
}

fun RatingKinds<RatingGroup>.combined() =
    RatingKind.entries.fold(RatingGroup.default) { acc, kind ->
        val ratingGroup = get(kind)
        RatingGroup(
            acc.sum + ratingGroup.sum,
            acc.count + ratingGroup.count,
            audience = max(acc.audience, ratingGroup.audience),
        )
    }