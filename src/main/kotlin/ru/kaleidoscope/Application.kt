package ru.kaleidoscope

import io.ktor.server.application.*
import io.ktor.server.netty.*
import kotlinx.coroutines.runBlocking
import ru.kaleidoscope.db.DatabaseFactory
import ru.kaleidoscope.db.dao.CodesDAO
import ru.kaleidoscope.db.dao.CodesDAOImpl
import ru.kaleidoscope.plugins.configureCORS
import ru.kaleidoscope.plugins.configureJWT
import ru.kaleidoscope.plugins.configureSerialization
import ru.kaleidoscope.routing.configureAuthRouting
import ru.kaleidoscope.routing.configureBaseRouting
import ru.kaleidoscope.routing.configureCodeUseRouting
import ru.kaleidoscope.routing.configureLoginRouting

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    //порядок важен
    DatabaseFactory.init()
    val codesDAO = createCodesDAO()
    configureCORS()
    configureJWT(codesDAO)
    configureRouting(codesDAO)
    configureSerialization()
}

private fun Application.configureRouting(codesDAO: CodesDAO) {
    configureLoginRouting(codesDAO)
    configureCodeUseRouting(codesDAO)
    configureAuthRouting()
    configureBaseRouting()
}

private fun createCodesDAO(): CodesDAO =
    CodesDAOImpl().apply {
//        runBlocking {
//            //todo remove
//            createCodes(1)
//        }
    }

