package cz.lastaapps.base.error.util

import arrow.core.Either
import cz.lastaapps.base.ErrorOutcome
import cz.lastaapps.base.Outcome
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond
import kotlinx.serialization.Serializable

// to prevent unintentional responding with Outcome and not its content
@Suppress("unused", "UNUSED_PARAMETER", "UnusedReceiverParameter")
fun <T : Any> ApplicationCall.respond(res: Outcome<T>): Nothing = error("Cannot respond with Outcome only")

suspend inline fun ApplicationCall.respondWithError(error: Either.Left<ErrorOutcome>) = respondWithError(error.value)

@Serializable
private data class ErrorPayload(
    val name: String,
    val message: String?,
)

suspend fun ApplicationCall.respondWithError(error: ErrorOutcome) {
    respond(error.httpCode, error.toPayload())
}

private fun ErrorOutcome.toPayload() = ErrorPayload(this::class.simpleName!!, message)
