package cz.lastaapps.app.domain.model

data class RatingRequest(
    val menzaID: MenzaID,
    val dishId: DishID,
    val kinds: RatingKinds<RatingValue>,
)
