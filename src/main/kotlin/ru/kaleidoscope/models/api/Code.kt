package ru.kaleidoscope.models.api

import kotlinx.serialization.Serializable

@Serializable
data class CodeReceive(
    val code: String
)

@Serializable
data class LoginResponse(
    val isCodeRight: Boolean,
    val token: String? = null
)

@Serializable
data class CodeUseResponse(
    val attemptsLeft: Int
)