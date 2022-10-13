package cz.lastaapps.app

import cz.lastaapps.app.config.ServerConfig
import cz.lastaapps.app.domain.model.auth.AppPrincipal
import cz.lastaapps.app.presentation.Routes
import dev.forst.ktor.apikey.apiKey
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.autohead.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.compression.*
import io.ktor.server.plugins.conditionalheaders.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.plugins.forwardedheaders.*
import io.ktor.server.plugins.httpsredirect.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.json.Json
import org.koin.ktor.ext.get
import org.koin.ktor.plugin.Koin


fun main(args: Array<String>) = io.ktor.server.cio.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    val app = this
    val config = ServerConfig.from(environment.config)

    install(Koin) {
        modules(
            org.koin.dsl.module {
                single { app }
                single<CoroutineScope> { app }
                single { environment.config }
                single { config }
            },
            appModule,
        )
    }

    install(ContentNegotiation) {
        json(Json {
            encodeDefaults = true
//            ignoreUnknownKeys = true
            prettyPrint = true
        })
    }

    install(AutoHeadResponse)

    install(DefaultHeaders)

    install(Authentication) {
        apiKey {
            validate { keyFromHeader ->
                keyFromHeader
                    .takeIf { it in config.apiKeys || config.apiKeys.isEmpty() }
                    ?.let { AppPrincipal(it) }
            }
        }
    }

    install(Compression) {
        matchContentType(ContentType.Application.Json)
        gzip {
            priority = 0.9
        }
        deflate {
            priority = 1.0
        }
    }

    install(StatusPages) {
        exception<Throwable> { call, cause ->
            when (cause) {
                is BadRequestException ->
                    call.respondText(
                        "400: Bad request - invalid data format, see open source docs",
                        status = HttpStatusCode.BadRequest,
                    )

                else -> call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
            }
        }
        status(HttpStatusCode.NotFound) { call, status ->
            call.respondText(text = "404: Page Not Found, see project docs", status = status)
        }
    }

    install(ForwardedHeaders)

    if (config.usesSSL) {
        install(HttpsRedirect) {
            sslPort = config.sslPort ?: error("You have to specify a secure port to redirect to HTTPS")
            permanentRedirect = true
        }
    }

    install(CallLogging) {
        format { call ->
            val status = call.response.status()
            val httpMethod = call.request.httpMethod.value
            val remoteHost = call.request.origin.remoteHost
//            val userAgent = call.request.headers["User-Agent"]
            val route = call.request.uri
            "Status: $status, HTTP method: $httpMethod, Route: $route, Remote Host: $remoteHost"
        }
    }

    get<Routes>().register()
}