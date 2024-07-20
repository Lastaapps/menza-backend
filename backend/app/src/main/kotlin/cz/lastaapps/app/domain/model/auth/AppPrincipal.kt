package cz.lastaapps.app.domain.model.auth

import io.ktor.server.auth.Principal

data class AppPrincipal(val key: String) : Principal
