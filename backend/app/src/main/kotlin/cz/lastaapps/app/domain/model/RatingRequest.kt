package cz.lastaapps.app.domain.model

data class RatingRequest(
    val menzaID: MenzaID,
    val dishName: DishName,
    val kinds: RatingKinds<RatingValue>,
)
