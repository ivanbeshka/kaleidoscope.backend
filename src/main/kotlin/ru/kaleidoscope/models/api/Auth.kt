package ru.kaleidoscope.models.api

import kotlinx.serialization.Serializable

@Serializable
data class AuthToken(val token: String)