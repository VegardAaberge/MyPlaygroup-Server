package no.vegardaaberge.routes

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import no.vegardaaberge.controllers.AuthController
import no.vegardaaberge.data.requests.AccountRequest

fun Route.registerRoute(authController: AuthController){
    post("/register") {
        val request = try {
            call.receive<AccountRequest>()
        }catch (e: ContentTransformationException){
            call.respond(HttpStatusCode.BadRequest, e.message.toString())
            return@post
        }

        val registerResponse = authController.tryRegister(request.username, request.password);

        call.respond(HttpStatusCode.OK, registerResponse)
    }
}

fun Route.loginRoute(authController: AuthController) {
    post("/login") {
        val request = try {
            call.receive<AccountRequest>()
        }catch (e: ContentTransformationException){
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val loginResponse = authController.tryLogin(request.username, request.password)

        call.respond(HttpStatusCode.OK, loginResponse)
    }
}