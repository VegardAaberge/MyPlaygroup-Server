package no.vegardaaberge.plugins

import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import no.vegardaaberge.controllers.AuthController
import no.vegardaaberge.controllers.ChatController
import no.vegardaaberge.routes.getAllMessages
import no.vegardaaberge.routes.loginRoute
import no.vegardaaberge.routes.registerRoute
import no.vegardaaberge.routes.sendChatMessage
import org.koin.ktor.ext.inject

fun Application.configureRouting() {

    val authController: AuthController by inject()
    val chatController: ChatController by inject()

    routing {
        loginRoute(authController)
        registerRoute(authController)
        getAllMessages(chatController)
        sendChatMessage(chatController)
    }
}
