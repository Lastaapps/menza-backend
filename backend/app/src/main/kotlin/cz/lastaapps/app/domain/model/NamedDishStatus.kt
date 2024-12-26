package cz.lastaapps.app.domain.model

data class NamedDishStatus(
    val dishDescriptor: DishDescriptor,
    val categories: RatingKinds<RatingGroup>,
)