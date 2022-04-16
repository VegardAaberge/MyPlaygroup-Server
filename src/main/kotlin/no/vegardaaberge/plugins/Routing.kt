package no.vegardaaberge.plugins

import io.ktor.server.routing.*
import io.ktor.server.application.*
import no.vegardaaberge.routes.loginRoute
import no.vegardaaberge.routes.registerRoute

fun Application.configureRouting() {
    routing {
        loginRoute()
        registerRoute()
    }
}
