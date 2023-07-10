package ru.kaleidoscope.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import ru.kaleidoscope.utils.AUTH_JWT

fun Application.configureJWT() {

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
//                if (credential.payload.getClaim("username").asString() != "") {
//                    JWTPrincipal(credential.payload)
//                } else {
//                    null
//                }
                JWTPrincipal(credential.payload)
            }
            challenge { _, _ ->
                //todo вынести сообщение в константу
                call.respond(HttpStatusCode.Unauthorized, "Token is not valid or has expired")
            }
        }
    }
}