package cz.lastaapps.app.config

import io.ktor.server.application.host
import io.ktor.server.config.ApplicationConfig

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
    val maxDishes: Int,
    val maxPerDay: Int,
) {
    companion object {
        fun from(config: ApplicationConfig) =
            with(config) {
                ServerConfig(
                    host,
                    propertyOrNull("app.deployment.port")?.getString()?.toInt(),
                    propertyOrNull("app.deployment.sslPort")?.getString()?.toInt(),
                    property("app.useSSL").getString().toBoolean(),
                    property("app.apiKeys")
                        .getString()
                        .split(",")
                        .map { it.trim() }
                        .filter { it.isNotBlank() }
                        .toSet(),
                    property("app.environment").getString().let {
                        when (it) {
                            "prod" -> Environment.PRODUCTION
                            "dev" -> Environment.DEVELOPMENT
                            else -> error("Unsupported env: $it")
                        }
                    },
                    property("app.maxDishes").getString().toInt(),
                    property("app.maxPerDay").getString().toInt(),
                )
            }
    }
}
