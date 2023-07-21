package ru.kaleidoscope.db

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.config.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import ru.kaleidoscope.db.models.CodeModel

object DatabaseFactory {
    fun init(config: ApplicationConfig) {

        val hikariConfig = initHikariConfig(config)

        val database = Database.connect(HikariDataSource(hikariConfig))
        transaction(database) {
            SchemaUtils.create(CodeModel)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

    private fun initHikariConfig(config: ApplicationConfig): HikariConfig {
        val hikariConfig = HikariConfig()
        hikariConfig.driverClassName = config.property("storage.driverClassName").getString()
        hikariConfig.jdbcUrl = config.property("storage.jdbcURL").getString()
        hikariConfig.password = config.property("storage.password").getString()
        hikariConfig.username = config.property("storage.username").getString()
        return hikariConfig
    }
}