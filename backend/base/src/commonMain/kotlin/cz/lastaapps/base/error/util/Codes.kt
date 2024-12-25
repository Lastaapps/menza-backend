package cz.lastaapps.base.error.util

import cz.lastaapps.base.ErrorOutcome
import cz.lastaapps.base.error.RatingError
import io.ktor.http.HttpStatusCode

internal val ErrorOutcome.httpCode: HttpStatusCode
    get() =
        when (this) {
            is RatingError -> httpCode
            else -> error("Unset code for exception: ${this::class.simpleName}")
        }

private val RatingError.httpCode: HttpStatusCode
    get() =
        when (this) {
            is RatingError.RatingInvalidRange,
            is RatingError.InvalidIdLength,
            is RatingError.InvalidDishName,
            is RatingError.InvalidMenzaID,
            is RatingError.InvalidRatingValue,
            -> HttpStatusCode.BadRequest

            is RatingError.MenzaQuotaReached,
            is RatingError.DishQuotaReached,
            is RatingError.RequestQuotaReached,
            -> HttpStatusCode.TooManyRequests
        }
