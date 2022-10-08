package cz.lastaapps.app.domain.model

data class DishProgress(
    val ratingSum: UInt,
    val rateCount: UInt,
    val soldOutCount: UInt,
) {
    companion object {
        internal val default = DishProgress(0u, 0u, 0u)
    }
}
