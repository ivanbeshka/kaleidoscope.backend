package ru.kaleidoscope.routing

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.kaleidoscope.db.dao.CodesDAO
import ru.kaleidoscope.routing.models.CodeReceive
import ru.kaleidoscope.routing.models.LoginResponse
import ru.kaleidoscope.utils.CLAIM_CODE
import ru.kaleidoscope.utils.JWT_LIFETIME
import java.util.*

fun Application.configureLoginRouting(codesDAO: CodesDAO) {

    val secret = environment.config.property("jwt.secret").getString()

    routing {

        post("/login") {
            val code = call.receive<CodeReceive>().code

            if (codesDAO.isCodeExists(code)) {
                val token = JWT
                    .create()
                    .withClaim(CLAIM_CODE, code)
                    .withExpiresAt(Date(System.currentTimeMillis() + JWT_LIFETIME))
                    .sign(Algorithm.HMAC256(secret))
                call.respond(LoginResponse(token))
            } else {
                call.respond(LoginResponse(null))
            }
        }
    }
}