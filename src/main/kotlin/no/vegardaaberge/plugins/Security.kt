package no.vegardaaberge.plugins

import io.ktor.server.sessions.*
import io.ktor.server.auth.*
import io.ktor.util.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import no.vegardaaberge.data.checkPasswordForEmail

fun Application.configureSecurity() {
    //data class MySession(val count: Int = 0)
    //install(Sessions)
}