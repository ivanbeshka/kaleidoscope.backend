package ru.kaleidoscope.routing.models

import kotlinx.serialization.Serializable

@Serializable
data class CodeReceive(
    val code: String
)

@Serializable
data class LoginResponse(
    val token: String? = null
)

@Serializable
data class CodeUseResponse(
    val attemptsLeft: Int
)