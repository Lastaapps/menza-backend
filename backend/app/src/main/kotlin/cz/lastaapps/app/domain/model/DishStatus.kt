package cz.lastaapps.app.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class DishStatus(
    val id: String,
    val ratingSum: UInt,
    val rateCount: UInt,
    val rating: Float?,
    val soldOutCount: UInt,
)
