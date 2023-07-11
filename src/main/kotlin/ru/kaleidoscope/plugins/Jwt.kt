package ru.kaleidoscope.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import ru.kaleidoscope.utils.AUTH_JWT
import ru.kaleidoscope.utils.CLAIM_CODE
import ru.kaleidoscope.utils.JWT_NOT_VALID

fun Application.configureJwt() {

//    secret = "secret"
//    issuer = "http://0.0.0.0:8080/" or sso.organization.com
//    audience = "http://0.0.0.0:8080/hello" or ios.organization.appname
//    realm = "Access to 'hello'"

    val secret = environment.config.property("jwt.secret").getString()
//    val issuer = environment.config.property("jwt.issuer").getString()
//    val audience = environment.config.property("jwt.audience").getString()
    val myRealm = environment.config.property("jwt.realm").getString()

    install(Authentication) {
        jwt(AUTH_JWT) {
            //hs256
            realm = myRealm
            verifier(
                JWT
                    .require(Algorithm.HMAC256(secret))
//                .withAudience(audience)
//                .withIssuer(issuer)
                    .build()
            )
            validate { credential ->

                val code = credential.payload.getClaim(CLAIM_CODE).asString()
                //todo check if code exists
                if (code == "right code") {
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