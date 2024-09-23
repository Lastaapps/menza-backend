package cz.lastaapps.app.domain.model.dto

import cz.lastaapps.app.domain.model.RepoStatistics
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class StatisticsDto(
    @SerialName("ratings")
    val ratings: UInt,
    @SerialName("average")
    val average: Float,
    @SerialName("sold_out")
    val soldOut: UInt,
    @SerialName("state_requests")
    val state: UInt,
    @SerialName("statistics_requests")
    val statistics: UInt,
)

internal fun RepoStatistics.toDto() = StatisticsDto(ratings, average, soldOut, state, statistics)
