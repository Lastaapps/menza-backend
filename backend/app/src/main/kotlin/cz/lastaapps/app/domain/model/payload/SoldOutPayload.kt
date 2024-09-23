package cz.lastaapps.app.domain.model.payload

import kotlinx.serialization.Serializable

@Serializable
data class SoldOutPayload(
    val id: String,
)
