package ru.kaleidoscope.routing

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.kaleidoscope.utils.AUTH_JWT

fun Application.configureAuthRouting() {

    routing {
        authenticate(AUTH_JWT) {
            get("/is-auth") {
                call.respond(HttpStatusCode.OK)
            }
        }
    }
}