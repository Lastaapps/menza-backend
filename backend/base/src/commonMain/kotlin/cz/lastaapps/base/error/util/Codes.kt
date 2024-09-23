package cz.lastaapps.base.error.util

import cz.lastaapps.base.ErrorResult
import cz.lastaapps.base.error.RatingError
import io.ktor.http.HttpStatusCode

internal val ErrorResult.httpCode: HttpStatusCode
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
            -> HttpStatusCode.BadRequest

            is RatingError.DishQuotaReached,
            is RatingError.RequestQuotaReached,
            -> HttpStatusCode.TooManyRequests
        }
