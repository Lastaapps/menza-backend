package cz.lastaapps.app.presentation

import arrow.core.Either
import cz.lastaapps.app.domain.model.dto.toDto
import cz.lastaapps.app.domain.model.payload.RatePayload
import cz.lastaapps.app.domain.model.payload.SoldOutPayload
import cz.lastaapps.app.domain.usecase.CacheStateUseCase
import cz.lastaapps.app.domain.usecase.GetRatingStateUseCase
import cz.lastaapps.app.domain.usecase.GetStatisticsUseCase
import cz.lastaapps.app.domain.usecase.RateUseCase
import cz.lastaapps.app.domain.usecase.SoldOutUseCase
import cz.lastaapps.base.error.util.respondWithError
import io.ktor.http.ContentType
import io.ktor.server.application.Application
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.routing.routing

internal class Routes(
    private val app: Application,
    private val getState: GetRatingStateUseCase,
    private val cached: CacheStateUseCase,
    private val rate: RateUseCase,
    private val soldOut: SoldOutUseCase,
    private val statistics: GetStatisticsUseCase,
) {
    fun register() {
        app.routing {
            authenticate {
                route("api/v1") {
                    rateEP()
                    soldOutEP()
                    statusEP()
                    statisticsEP()
                }
            }
        }
    }

    private fun Route.rateEP() {
        post("rate") {
            val payload = call.receive<RatePayload>()
            val params = RateUseCase.Params(payload.id, payload.rating)

            when (val res = rate(params)) {
                is Either.Right -> call.respondText(cached(), ContentType.Application.Json)
                is Either.Left -> call.respondWithError(res)
            }
        }
    }

    private fun Route.soldOutEP() {
        post("sold-out") {
            val payload = call.receive<SoldOutPayload>()
            val params = SoldOutUseCase.Params(payload.id)

            when (val res = soldOut(params)) {
                is Either.Right -> call.respondText(cached(), ContentType.Application.Json)
                is Either.Left -> call.respondWithError(res)
            }
        }
    }

    private fun Route.statusEP() {
        get("status") {
            call.respondText(getState(), ContentType.Application.Json)
        }
    }

    private fun Route.statisticsEP() {
        get("statistics") {
            call.respond(statistics().toDto())
        }
    }
}
