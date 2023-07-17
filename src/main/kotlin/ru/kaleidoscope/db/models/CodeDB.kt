package ru.kaleidoscope.db.models

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date
import ru.kaleidoscope.utils.ATTEMPTS_LEFT
import ru.kaleidoscope.utils.CODE_LENGTH
import ru.kaleidoscope.utils.CODE_LIFETIME
import java.time.LocalDate

data class CodeDB(
    val id: Int,
    val code: String,
    val attemptsLeft: Int,
    val expirationDate: LocalDate
)

object CodeModel : Table("codes") {
    val id = integer("id").autoIncrement()
    val code = varchar("code", CODE_LENGTH)
    val attemptsLeft = integer("attempts_left").default(ATTEMPTS_LEFT)
    val expirationDate = date("expiration_date").default(LocalDate.now().plusDays(CODE_LIFETIME))

    override val primaryKey = PrimaryKey(id)
}