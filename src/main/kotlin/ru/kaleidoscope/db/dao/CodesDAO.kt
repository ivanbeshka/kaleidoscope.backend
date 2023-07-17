package ru.kaleidoscope.db.dao

import ru.kaleidoscope.db.models.CodeDB

interface CodesDAO {
    suspend fun isCodeExists(code: String): Boolean
    suspend fun createCodes(count: Int): List<CodeDB>
    suspend fun minusAttempt(code: String): Boolean
    suspend fun deleteCodes(): Boolean
    suspend fun getCodeAttempts(code: String): Int?
}