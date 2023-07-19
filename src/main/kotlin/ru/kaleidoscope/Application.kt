package ru.kaleidoscope

import io.ktor.server.application.*
import io.ktor.server.netty.*
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
import java.io.File

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    //порядок важен
    val dbURL = environment.config.property("ktor.deployment.db_path").getString()
    val file = File("f")
    file.createNewFile()
    println(file.toURI().toURL().toString())
    File("/").walkTopDown().forEach { println(it) }
    DatabaseFactory.init(dbURL)
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

