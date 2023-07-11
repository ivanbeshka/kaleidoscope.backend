package ru.kaleidoscope.routing

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.kaleidoscope.models.api.CodeReceive
import ru.kaleidoscope.models.api.CodeUseResponse
import ru.kaleidoscope.utils.AUTH_JWT

fun Application.configureCodeUseRouting() {

    routing {
        authenticate(AUTH_JWT) {
            post("/code-use") {
                val receive = call.receive<CodeReceive>()
                //todo call db
                call.respond(CodeUseResponse(2))
            }
        }
    }
}