package ru.kaleidoscope.routing

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureBaseRouting() {

    routing {
        get("/") {
            call.respond(HttpStatusCode.OK)
        }
    }
}