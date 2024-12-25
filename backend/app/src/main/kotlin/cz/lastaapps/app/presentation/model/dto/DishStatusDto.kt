package cz.lastaapps.app.presentation.model.dto

import cz.lastaapps.app.domain.model.DishStatus
import cz.lastaapps.app.domain.model.RatingKind
import cz.lastaapps.app.domain.model.combined
import kotlinx.serialization.Serializable

@Serializable
data class DishStatusDto(
    val id: String,
    val combined: RatingDto,
    val taste: RatingDto,
    val portion: RatingDto,
    val worthiness: RatingDto,
)


fun List<DishStatus>.toDto() = map { it.toDto() }

fun DishStatus.toDto() = DishStatusDto(
    id = id.id,
    combined = categories.combined().toDto(),
    taste = categories.get(RatingKind.TASTE).toDto(),
    portion = categories.get(RatingKind.PORTION).toDto(),
    worthiness = categories.get(RatingKind.WORTHINESS).toDto(),
)