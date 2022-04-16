package no.vegardaaberge.routes

import io.ktor.http.*
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.application.*
import io.ktor.server.plugins.ContentTransformationException
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import no.vegardaaberge.data.checkPasswordForEmail
import no.vegardaaberge.data.requests.AccountRequest
import no.vegardaaberge.data.responses.SimpleResponse

fun Route.loginRoute() {
    post("/login") {
        val request = try {
            call.receive<AccountRequest>()
        }catch (e: ContentTransformationException){
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }
        val isPasswordCorrect = checkPasswordForEmail(request.username, request.password)
        if(isPasswordCorrect){
            call.respond(OK, SimpleResponse(true, "You are now logged in"))
        }else{
            call.respond(OK, SimpleResponse(false, "The email or password is incorrect"))
        }
    }
}