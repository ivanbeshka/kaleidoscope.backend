package ru.kaleidoscope.utils

//auth
const val AUTH_JWT = "auth-jwt"
const val JWT_LIFETIME = 86400000 //1 day
const val CLAIM_CODE = "code"

//db
const val CODE_LIFETIME = 100L //days
const val ATTEMPTS_LEFT = 3
const val CODE_LENGTH = 8 //XXXX-XXXX

//bot
const val CODES_MIN_AMOUNT = 10
const val CODES_MAX_AMOUNT = 100
const val CODES_FONT_SIZE = 15
