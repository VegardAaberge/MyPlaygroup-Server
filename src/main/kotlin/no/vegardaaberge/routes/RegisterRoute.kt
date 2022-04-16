package no.vegardaaberge.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.ContentTransformationException
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import no.vegardaaberge.data.checkIfUserExists
import no.vegardaaberge.data.model.User
import no.vegardaaberge.data.registerUser
import no.vegardaaberge.data.requests.AccountRequest
import no.vegardaaberge.data.responses.SimpleResponse

fun Route.registerRoute(){
    post("/register") {
        val request = try {
            call.receive<AccountRequest>()
        }catch (e: ContentTransformationException){
            call.respond(HttpStatusCode.BadRequest, e.message.toString())
            return@post
        }

        val userExist = checkIfUserExists(request.username)
        if(!userExist){
            val newUser = User(request.username, request.password)
            if(registerUser(newUser)){
                call.respond(HttpStatusCode.OK, SimpleResponse(true, "Successfully created an account"))
            }else{
                call.respond(HttpStatusCode.OK, SimpleResponse(false, "An unknown error occurred"))
            }
        }else{
            call.respond(HttpStatusCode.OK, SimpleResponse(false, "A user with that email already exist"))
        }
    }
}