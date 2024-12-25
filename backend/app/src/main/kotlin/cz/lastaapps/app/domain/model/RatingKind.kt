package cz.lastaapps.app.domain.model

enum class RatingKind {
    /**
     * How well does the food taste
     */
    TASTE,

    /**
     * If the portion is big enough
     */
    PORTION,

    /**
     * If the user would choose the same dish again (for the price)
     */
    WORTHINESS,
}

class RatingKinds<T>(
    val taste: T,
    val portion: T,
    val worthiness: T,
) {
    @Suppress("NOTHING_TO_INLINE")
    inline fun get(kind: RatingKind) = when (kind) {
        RatingKind.TASTE -> taste
        RatingKind.PORTION -> portion
        RatingKind.WORTHINESS -> worthiness
    }
}