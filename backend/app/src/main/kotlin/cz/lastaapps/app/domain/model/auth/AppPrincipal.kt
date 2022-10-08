package cz.lastaapps.app.domain.model.auth

import io.ktor.server.auth.*

data class AppPrincipal(val key: String) : Principal
