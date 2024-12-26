package cz.lastaapps.app.domain.model

data class DishStatus(
    val dishID: DishID,
    val categories: RatingKinds<RatingGroup>,
)

typealias DishStatusList = List<DishStatus>
