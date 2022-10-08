package cz.lastaapps.base.util

import io.ktor.server.application.*
import io.ktor.util.pipeline.*

val <T : Any> PipelineContext<T, ApplicationCall>.params get() = call.request.queryParameters