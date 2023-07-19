package ru.kaleidoscope.db

import io.ktor.http.*
import io.ktor.server.application.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import ru.kaleidoscope.db.models.CodeModel

object DatabaseFactory {
    fun init(jdbcURL: String) {
        val database = Database.connect(jdbcURL, DRIVER_CLASS_NAME)
        transaction(database) {
//            SchemaUtils.create(CodeModel)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

    private const val DRIVER_CLASS_NAME = "org.h2.Driver"
}