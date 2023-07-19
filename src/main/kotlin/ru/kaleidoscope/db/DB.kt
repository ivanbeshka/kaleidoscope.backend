package ru.kaleidoscope.db

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun init() {
        val database = Database.connect(JDBC_URL, DRIVER_CLASS_NAME)
        transaction(database) {
//            SchemaUtils.create(CodeModel)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

    private const val DRIVER_CLASS_NAME = "org.h2.Driver"
    private const val JDBC_URL = "jdbc:h2:file:./build/db"
}