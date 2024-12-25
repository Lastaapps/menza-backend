package cz.lastaapps.app.domain.model

data class DishStatus(
    val id: DishName,
    val categories: RatingKinds<RatingGroup>,
)

typealias DishStatusList = List<DishStatus>
