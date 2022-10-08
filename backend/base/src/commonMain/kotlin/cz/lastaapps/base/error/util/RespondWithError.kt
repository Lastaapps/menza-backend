package cz.lastaapps.base.error.util

import cz.lastaapps.base.ErrorResult
import cz.lastaapps.base.Result
import io.ktor.server.application.*
import io.ktor.server.response.*
import kotlinx.serialization.Serializable

// to prevent unintentional responding with result and not its content
@Suppress("unused", "UNUSED_PARAMETER")
fun <T : Any> ApplicationCall.respond(res: Result<T>): Nothing = error("Cannot respond with pure result")

suspend inline fun <T : Any> ApplicationCall.respondWithError(error: Result.Error<T>) = respondWithError(error.error)

@Serializable
private data class ErrorPayload(
    val name: String,
    val message: String?,
)

suspend fun ApplicationCall.respondWithError(error: ErrorResult) {
    respond(error.httpCode, error.toPayload())
}

private fun ErrorResult.toPayload() = ErrorPayload(this::class.simpleName!!, message)
