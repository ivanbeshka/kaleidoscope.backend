package ru.kaleidoscope.models.api

import kotlinx.serialization.Serializable

@Serializable
data class CodeReceive(
    val code: String
)

@Serializable
data class CodeResponse(
    val isRight: Boolean,
    val attemptsLeft: Int?
)