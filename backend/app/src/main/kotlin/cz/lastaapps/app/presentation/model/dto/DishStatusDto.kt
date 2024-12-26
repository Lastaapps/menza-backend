package cz.lastaapps.app.presentation.model.dto

import cz.lastaapps.app.domain.model.NamedDishStatus
import cz.lastaapps.app.domain.model.RatingKind
import cz.lastaapps.app.domain.model.combined
import kotlinx.serialization.Serializable

@Serializable
data class DishStatusDto(
    val id: String,
    val nameCs: String?,
    val nameEn: String?,
    val combined: RatingDto,
    val taste: RatingDto,
    val portion: RatingDto,
    val worthiness: RatingDto,
)


fun List<NamedDishStatus>.toDto() = map { it.toDto() }

fun NamedDishStatus.toDto() = DishStatusDto(
    id = dishDescriptor.id.id,
    nameCs = dishDescriptor.nameCs?.value,
    nameEn = dishDescriptor.nameEn?.value,
    combined = categories.combined().toDto(),
    taste = categories.get(RatingKind.TASTE).toDto(),
    portion = categories.get(RatingKind.PORTION).toDto(),
    worthiness = categories.get(RatingKind.WORTHINESS).toDto(),
)