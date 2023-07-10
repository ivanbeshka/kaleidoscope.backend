package ru.kaleidoscope.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.kaleidoscope.models.api.AuthToken
import ru.kaleidoscope.utils.AUTH_JWT
import ru.kaleidoscope.utils.JWT_LIFETIME
import java.util.*

fun Application.configureRouting() {

    val secret = environment.config.property("jwt.secret").getString()
//    val issuer = environment.config.property("jwt.issuer").getString()
//    val audience = environment.config.property("jwt.audience").getString()

    routing {

        post("/login") {
//            val user = call.receive<User>()
            // Check username and password
            // ...

            val token = JWT.create()
//                .withAudience(audience)
//                .withIssuer(issuer)
//                .withClaim("username", user.username)
                .withExpiresAt(Date(System.currentTimeMillis() + JWT_LIFETIME))
                .sign(Algorithm.HMAC256(secret))
            call.respond(AuthToken(token))
        }

        authenticate(AUTH_JWT) {

            get("/code") {
                call.respondText("Hello World!")
            }

        }
    }
}
