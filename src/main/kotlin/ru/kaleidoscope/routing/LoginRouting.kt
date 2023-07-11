package ru.kaleidoscope.routing

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.kaleidoscope.models.api.CodeReceive
import ru.kaleidoscope.models.api.LoginResponse
import ru.kaleidoscope.utils.CLAIM_CODE
import ru.kaleidoscope.utils.JWT_LIFETIME
import java.util.*

fun Application.configureLoginRouting() {

    val secret = environment.config.property("jwt.secret").getString()
//    val issuer = environment.config.property("jwt.issuer").getString()
//    val audience = environment.config.property("jwt.audience").getString()

    routing {

        post("/login") {
            val receive = call.receive<CodeReceive>()

            //todo check code in db
            if (receive.code == "right code") {
                val token = JWT.create()
//                .withAudience(audience)
//                .withIssuer(issuer)
                    .withClaim(CLAIM_CODE, receive.code)
                    .withExpiresAt(Date(System.currentTimeMillis() + JWT_LIFETIME))
                    .sign(Algorithm.HMAC256(secret))
                call.respond(LoginResponse(true, token))
            } else {
                call.respond(LoginResponse(false))
            }
        }
    }
}