package ru.kaleidoscope

import io.ktor.server.application.*
import io.ktor.server.netty.*
import ru.kaleidoscope.db.DatabaseFactory
import ru.kaleidoscope.db.dao.CodesDAO
import ru.kaleidoscope.db.dao.CodesDAOImpl
import ru.kaleidoscope.plugins.configureCORS
import ru.kaleidoscope.plugins.configureJWT
import ru.kaleidoscope.plugins.configureSerialization
import ru.kaleidoscope.bot.configureTelegramBot
import ru.kaleidoscope.routing.configureAuthRouting
import ru.kaleidoscope.routing.configureBaseRouting
import ru.kaleidoscope.routing.configureCodeUseRouting
import ru.kaleidoscope.routing.configureLoginRouting

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    //порядок важен
    DatabaseFactory.init(environment.config)
    val codesDAO = createCodesDAO()
    configureCORS()
    configureJWT(codesDAO)
    configureRouting(codesDAO)
    configureSerialization()
    configureTelegramBot(codesDAO)
}

private fun Application.configureRouting(codesDAO: CodesDAO) {
    configureLoginRouting(codesDAO)
    configureCodeUseRouting(codesDAO)
    configureAuthRouting()
    configureBaseRouting()
}

private fun createCodesDAO(): CodesDAO = CodesDAOImpl()

