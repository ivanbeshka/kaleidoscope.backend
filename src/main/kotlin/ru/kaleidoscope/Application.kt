package ru.kaleidoscope

import io.ktor.server.application.*
import io.ktor.server.netty.*
import ru.kaleidoscope.plugins.configureJWT
import ru.kaleidoscope.plugins.configureRouting
import ru.kaleidoscope.plugins.configureSerialization

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    //порядок важен
    configureJWT()
    configureRouting()
    configureSerialization()
}
