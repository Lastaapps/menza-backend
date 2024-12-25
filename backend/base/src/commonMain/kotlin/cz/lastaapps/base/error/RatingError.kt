package cz.lastaapps.base.error

import cz.lastaapps.base.ErrorOutcome

sealed class RatingError(
    message: String? = null,
    throwable: Throwable? = null,
) : ErrorOutcome(message, throwable) {
    class RatingInvalidRange : RatingError("Rating must be an integer between 1 and 5")

    class InvalidIdLength : RatingError("Id length must be 8")

    class RequestQuotaReached : RatingError("Request quota reached for today - to many ratings")

    class MenzaQuotaReached : RatingError("Menza quota reached for today - to many ratings")

    class DishQuotaReached : RatingError("Dish quota reached for today - to many ratings")

    class InvalidMenzaID : RatingError("Invalid menza id")

    class InvalidDishName : RatingError("Invalid dish name")

    class InvalidRatingValue : RatingError("Invalid rating value")
}
