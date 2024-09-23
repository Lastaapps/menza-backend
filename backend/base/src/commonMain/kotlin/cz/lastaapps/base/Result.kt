package cz.lastaapps.base

import arrow.core.Either

typealias Outcome<T> = Either<ErrorOutcome, T>

open class ErrorOutcome(
    val message: String?,
    val throwable: Throwable?,
)
