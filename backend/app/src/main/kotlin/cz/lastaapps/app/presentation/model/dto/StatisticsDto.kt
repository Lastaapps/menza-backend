package cz.lastaapps.app.presentation.model.dto

import cz.lastaapps.app.domain.model.RepoStatistics
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class StatisticsDto(
    @SerialName("rating_requests")
    val ratings: UInt,
    @SerialName("state_requests")
    val state: UInt,
    @SerialName("statistics_requests")
    val statistics: UInt,
)

internal fun RepoStatistics.toDto() = StatisticsDto(ratings, state, statistics)
