package ru.kaleidoscope.routing

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.kaleidoscope.db.dao.CodesDAO
import ru.kaleidoscope.routing.models.CodeReceive
import ru.kaleidoscope.routing.models.CodeUseResponse
import ru.kaleidoscope.utils.AUTH_JWT

fun Application.configureCodeUseRouting(codesDAO: CodesDAO) {

    routing {
        authenticate(AUTH_JWT) {
            post("/code-use") {
                val code = call.receive<CodeReceive>().code
                val isUpdated = codesDAO.minusAttempt(code)
                if (isUpdated) {
                    val attemptsLeft = codesDAO.getCodeAttempts(code)
                    attemptsLeft?.let {
                        call.respond(CodeUseResponse(it))
                    }
                    //todo
                    call.respond(HttpStatusCode.ExpectationFailed)
                }
                call.respond(HttpStatusCode.NotModified)
            }
        }
    }
}