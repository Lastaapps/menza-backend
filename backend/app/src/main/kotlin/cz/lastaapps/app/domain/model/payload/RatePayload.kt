package cz.lastaapps.app.domain.model.payload

import kotlinx.serialization.Serializable

@Serializable
internal data class RatePayload(
    val id: String,
    val rating: UInt,
)