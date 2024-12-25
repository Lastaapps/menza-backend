package cz.lastaapps.app.presentation

import arrow.core.Either
import arrow.core.raise.either
import cz.lastaapps.app.domain.model.DishName
import cz.lastaapps.app.domain.model.MenzaID
import cz.lastaapps.app.domain.model.RatingRequest
import cz.lastaapps.app.domain.usecase.GetRatingStateUseCase
import cz.lastaapps.app.domain.usecase.GetStatisticsUseCase
import cz.lastaapps.app.domain.usecase.RateUseCase
import cz.lastaapps.app.presentation.model.dto.toDto
import cz.lastaapps.app.presentation.model.payload.RatePayload
import cz.lastaapps.app.presentation.model.payload.toDomain
import cz.lastaapps.base.Outcome
import cz.lastaapps.base.error.util.respondWithError
import io.ktor.server.application.Application
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.RoutingCall
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.routing.routing

internal class Routes(
    private val app: Application,
    private val rateUC: RateUseCase,
    private val getStateUC: GetRatingStateUseCase,
    private val statisticsUC: GetStatisticsUseCase,
) {
    fun register() {
        app.routing {
            authenticate {
                route("api/v1") {
                    rateEP()
                    statusEP()
                    statisticsEP()
                }
            }
        }
    }

    private fun Route.rateEP() {
        post("rate/{menza_id}/{dish_name}") {
            val payload = call.receive<RatePayload>()
            val res = either {
                val menzaID = MenzaID.fromString(call.parameters["menza_id"]!!).bind()
                val dishName = DishName.fromString(call.parameters["dish_name"]!!).bind()
                val request = RatingRequest(
                    menzaID = menzaID,
                    dishName = dishName,
                    kinds = payload.toDomain().bind(),
                )
                rateUC(RateUseCase.Params(request)).bind()
            }

            call.respondOutcome(res.map { it.toDto() })
        }
    }

    private fun Route.statusEP() {
        get("status/{menza_id}") {
            val res = either {
                val menzaID = MenzaID.fromString(call.parameters["menza_id"]!!).bind()
                getStateUC(GetRatingStateUseCase.Param(menzaID))
            }

            call.respondOutcome(res.map { it.toDto() })
        }
    }

    private fun Route.statisticsEP() {
        get("statistics") {
            call.respond(statisticsUC().toDto())
        }
    }

    private suspend inline fun <reified T : Any> RoutingCall.respondOutcome(res: Outcome<T>) {
        when (res) {
            is Either.Right -> respond(res.value)
            is Either.Left -> respondWithError(res)
        }
    }
}
