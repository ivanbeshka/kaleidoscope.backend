package ru.kaleidoscope.plugins

import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*

fun Application.configureRouting() {
    routing {
        get("/code") {
            call.respondText("Hello World!")
        }
    }
}
