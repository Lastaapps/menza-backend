package cz.lastaapps.base.error

import cz.lastaapps.base.ErrorResult

sealed class RatingError(message: String? = null, throwable: Throwable? = null) : ErrorResult(message, throwable) {
    class RatingInvalidRange : RatingError("Rating must be an integer between 1 and 5")

    class InvalidIdLength : RatingError("Id length must be 8")

    class RequestQuotaReached : RatingError("Request quota reached for today - to many ratings")

    class DishQuotaReached : RatingError("Dish quota reached for today - to many ratings")
}
