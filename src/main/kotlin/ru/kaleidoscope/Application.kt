package ru.kaleidoscope

import io.ktor.server.application.*
import io.ktor.server.netty.*
import ru.kaleidoscope.plugins.configureJwt
import ru.kaleidoscope.plugins.configureSerialization
import ru.kaleidoscope.routing.configureAuthRouting
import ru.kaleidoscope.routing.configureCodeUseRouting
import ru.kaleidoscope.routing.configureLoginRouting

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    //порядок важен
    configureJwt()
    configureRouting()
    configureSerialization()
}

private fun Application.configureRouting() {
    configureLoginRouting()
    configureCodeUseRouting()
    configureAuthRouting()
}
