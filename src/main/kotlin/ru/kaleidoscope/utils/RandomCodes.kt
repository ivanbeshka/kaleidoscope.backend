package ru.kaleidoscope.utils

fun generateRandomCodes(count: Int): List<String> =
    List(count) {
        generateRandomCode()
    }

private fun generateRandomCode() : String {
    val charset = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    return List(CODE_LENGTH) { charset.random() }
        .joinToString("")
}