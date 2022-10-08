package cz.lastaapps.app.presentation

import cz.lastaapps.app.domain.model.payload.RatePayload
import cz.lastaapps.app.domain.model.payload.SoldOutPayload
import cz.lastaapps.app.domain.usecase.GetRatingStateUseCase
import cz.lastaapps.app.domain.usecase.RateUseCase
import cz.lastaapps.app.domain.usecase.SoldOutUseCase
import cz.lastaapps.base.Result
import cz.lastaapps.base.error.util.respondWithError
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

internal class Routes(
    private val app: Application,
    private val getState: GetRatingStateUseCase,
    private val rate: RateUseCase,
    private val soldOut: SoldOutUseCase,
) {
    fun register() {
        app.routing {
            authenticate {
                route("api/v1") {
                    rate()
                    soldOut()
                    status()
                }
            }
        }
    }

    private fun Route.rate() {
        post("rate") {
            val payload = call.receive<RatePayload>()
            val params = RateUseCase.Params(payload.id, payload.rating)

            when (val res = rate(params)) {
                is Result.Success -> call.respondText(getState(), ContentType.Application.Json)
                is Result.Error -> call.respondWithError(res)
            }
        }
    }

    private fun Route.soldOut() {
        post("sold-out") {
            val payload = call.receive<SoldOutPayload>()
            val params = SoldOutUseCase.Params(payload.id)

            when (val res = soldOut(params)) {
                is Result.Success -> call.respondText(getState(), ContentType.Application.Json)
                is Result.Error -> call.respondWithError(res)
            }
        }
    }

    private fun Route.status() {
        get("status") {
            call.respondText(getState(), ContentType.Application.Json)
        }
    }
}
