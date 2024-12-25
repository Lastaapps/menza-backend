package cz.lastaapps.app.presentation.model.dto

import cz.lastaapps.app.domain.model.RatingGroup
import kotlinx.serialization.Serializable

@Serializable
data class RatingDto(
    val sum: UInt,
    val count: UInt,
    val audience: UInt,
    val average: Float,
)

fun RatingGroup.toDto() = RatingDto(
    sum = sum,
    count = count,
    audience = audience,
    average = average,
)
