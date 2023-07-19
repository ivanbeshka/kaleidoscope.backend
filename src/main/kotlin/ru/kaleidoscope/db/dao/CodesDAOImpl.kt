package ru.kaleidoscope.db.dao

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.less
import ru.kaleidoscope.db.DatabaseFactory.dbQuery
import ru.kaleidoscope.db.models.CodeDB
import ru.kaleidoscope.db.models.CodeModel
import ru.kaleidoscope.utils.generateRandomCodes
import java.time.LocalDate

class CodesDAOImpl : CodesDAO {

    override suspend fun isCodeExists(code: String): Boolean = dbQuery {
        CodeModel
            .select { CodeModel.code eq code }
            .any()
    }

    override suspend fun createCodes(count: Int): List<CodeDB> = dbQuery {
        CodeModel
            .batchInsert(generateRandomCodes(count)) {
                this[CodeModel.code] = it
            }
            .insertCodes()
    }

    override suspend fun getCodeAttempts(code: String): Int? = dbQuery {
        CodeModel
            .select { CodeModel.code eq code }
            .firstOrNull()
            ?.get(CodeModel.attemptsLeft)
    }

    override suspend fun minusAttempt(code: String): Boolean = dbQuery {
        CodeModel
            .update({ CodeModel.code eq code }) {
                with(SqlExpressionBuilder) {
                    it.update(attemptsLeft, attemptsLeft - 1)
                }
            } > 0
    }

    override suspend fun deleteCodes(): Boolean = dbQuery {
        CodeModel
            .deleteWhere {
                expirationDate less LocalDate.now()
            } > 0
    }

    private fun ResultRow.toCodeDB() =
        CodeDB(
            id = this[CodeModel.id],
            code = this[CodeModel.code],
            attemptsLeft = this[CodeModel.attemptsLeft],
            expirationDate = this[CodeModel.expirationDate]
        )

    private fun List<ResultRow>.insertCodes() = map {
        it.toCodeDB()
    }
}