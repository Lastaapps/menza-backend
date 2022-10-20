package cz.lastaapps.app.domain.model

data class RepoStatistics(
    val ratings: UInt = 0u,
    val total: UInt = 0u,
    val soldOut: UInt = 0u,
    val state: UInt = 0u,
    val statistics: UInt = 0u,
) {
    val average get() = if (ratings == 0u) 0f else ratings.toFloat().div(ratings.toFloat())
}