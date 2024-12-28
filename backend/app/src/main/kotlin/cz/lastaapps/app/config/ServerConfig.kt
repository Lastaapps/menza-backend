package cz.lastaapps.app.config

import io.ktor.server.application.host
import io.ktor.server.config.ApplicationConfig
import kotlinx.datetime.TimeZone

enum class Environment {
    PRODUCTION,
    DEVELOPMENT,
}

data class ServerConfig(
    val host: String,
    val port: Int?,
    val sslPort: Int?,
    val usesSSL: Boolean,
    val apiKeys: Set<String>,
    val environment: Environment,
    val timeZone: TimeZone,
    val maxMenzas: Int,
    val maxDishes: Int,
    val maxPerDay: Int,
) {
    companion object {
        fun from(config: ApplicationConfig) =
            with(config) {
                ServerConfig(
                    host = host,
                    port = propertyOrNull("app.deployment.port")?.getString()?.toInt(),
                    sslPort = propertyOrNull("app.deployment.sslPort")?.getString()?.toInt(),
                    usesSSL = property("app.useSSL").getString().toBoolean(),
                    apiKeys = property("app.apiKeys")
                        .getString()
                        .split(",")
                        .map { it.trim() }
                        .filter { it.isNotBlank() }
                        .toSet(),
                    environment = property("app.environment").getString().let {
                        when (it) {
                            "prod" -> Environment.PRODUCTION
                            "dev" -> Environment.DEVELOPMENT
                            else -> error("Unsupported env: $it")
                        }
                    },
                    timeZone = TimeZone.of(property("app.timeZone").getString()),
                    maxMenzas = property("app.maxMenzas").getString().toInt(),
                    maxDishes = property("app.maxDishes").getString().toInt(),
                    maxPerDay = property("app.maxPerDay").getString().toInt(),
                )
            }
    }
}
