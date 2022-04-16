package no.vegardaaberge.routes

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import no.vegardaaberge.controllers.ChatController
import no.vegardaaberge.data.requests.ChatMessageRequest

fun Route.getAllMessages(chatController: ChatController) {
    get("/getChatMessages") {

        val messages = chatController.getAllMessages()

        call.respond(HttpStatusCode.OK, messages)
    }
}

fun Route.sendChatMessage(chatController: ChatController) {
    post("/sendChatMessage") {

        val request = try {
            call.receive<ChatMessageRequest>()
        }catch (e: ContentTransformationException){
            call.respond(HttpStatusCode.BadRequest, e.message.toString())
            return@post
        }

        val messageResponse = chatController.sendMessage(request.username, request.message)

        call.respond(HttpStatusCode.OK, messageResponse)
    }
}