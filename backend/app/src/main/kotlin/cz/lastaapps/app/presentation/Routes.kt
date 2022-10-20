package cz.lastaapps.app.presentation

import cz.lastaapps.app.domain.model.dto.toDto
import cz.lastaapps.app.domain.model.payload.RatePayload
import cz.lastaapps.app.domain.model.payload.SoldOutPayload
import cz.lastaapps.app.domain.usecase.*
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
                is Result.Success -> call.respondText(cached(), ContentType.Application.Json)
                is Result.Error -> call.respondWithError(res)
            }
        }
    }

    private fun Route.soldOutEP() {
        post("sold-out") {
            val payload = call.receive<SoldOutPayload>()
            val params = SoldOutUseCase.Params(payload.id)

            when (val res = soldOut(params)) {
                is Result.Success -> call.respondText(cached(), ContentType.Application.Json)
                is Result.Error -> call.respondWithError(res)
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
