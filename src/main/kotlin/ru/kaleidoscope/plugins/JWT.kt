package ru.kaleidoscope.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import ru.kaleidoscope.db.dao.CodesDAO
import ru.kaleidoscope.utils.AUTH_JWT
import ru.kaleidoscope.utils.CLAIM_CODE
import ru.kaleidoscope.utils.JWT_NOT_VALID

fun Application.configureJWT(codesDAO: CodesDAO) {

    val secret = environment.config.property("jwt.secret").getString()
    val myRealm = environment.config.property("jwt.realm").getString()

    install(Authentication) {
        jwt(AUTH_JWT) {
            //hs256
            realm = myRealm
            verifier(
                JWT
                    .require(Algorithm.HMAC256(secret))
                    .build()
            )
            validate { credential ->
                val code = credential.payload.getClaim(CLAIM_CODE).asString()

                if (codesDAO.isCodeExists(code)) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
            challenge { _, _ ->
                call.respond(HttpStatusCode.Unauthorized, JWT_NOT_VALID)
            }
        }
    }
}