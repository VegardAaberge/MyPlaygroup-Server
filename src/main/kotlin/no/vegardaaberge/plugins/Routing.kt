package no.vegardaaberge.plugins

import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import no.vegardaaberge.controllers.AuthController
import no.vegardaaberge.routes.loginRoute
import no.vegardaaberge.routes.registerRoute
import org.koin.ktor.ext.inject

fun Application.configureRouting() {

    val authController: AuthController by inject()

    routing {
        loginRoute(authController)
        registerRoute(authController)
    }
}
