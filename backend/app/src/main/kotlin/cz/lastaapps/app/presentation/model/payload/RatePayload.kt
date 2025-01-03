package cz.lastaapps.app.presentation.model.payload

import arrow.core.raise.either
import arrow.core.right
import cz.lastaapps.app.domain.model.RatingKinds
import cz.lastaapps.app.domain.model.RatingValue
import kotlinx.serialization.Serializable

@Serializable
internal data class RatePayload(
    val dishID: String? = null,
    val nameCs: String? = null,
    val nameEn: String? = null,
    val taste: UInt? = null,
    val portion: UInt? = null,
    val worthiness: UInt? = null,
)

internal fun RatePayload.toDomain() = either {
    fun convert(value: UInt?) = value?.let { RatingValue.from(it) } ?: RatingValue.empty.right()

    RatingKinds(
        taste = convert(taste).bind(),
        portion = convert(portion).bind(),
        worthiness = convert(worthiness).bind(),
    )
}
