package ru.kaleidoscope.bot

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.Dispatcher
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.dispatcher.handlers.CommandHandlerEnvironment
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.TelegramFile
import io.ktor.server.application.*
import ru.kaleidoscope.db.dao.CodesDAO
import ru.kaleidoscope.utils.*

fun Application.configureTelegramBot(codesDAO: CodesDAO) {

    val adminUsername = environment.config.property("bot.adminUsername").getString()
    createBotWithCommands {

        command("start") {
            if (adminUsername != message.from?.username) {
                return@command
            }

            bot.sendMessage(
                chatId = ChatId.fromId(message.chat.id),
                text = HELLO_ADMIN
            )
        }

        command("generate_codes") {
            if (adminUsername != message.from?.username) {
                return@command
            }

            val joinedArgs = args.joinToString()
            sendMessageIfArgsBlank(joinedArgs)

            val codesAmount = joinedArgs.toIntOrNull()
            sendMessageIfCodesAmountNull(codesAmount)

            codesAmount?.let {
                val isCodesAmountInRange = checkCodesAmountInRange(it)
                if (isCodesAmountInRange) {
                    bot.sendDocument(
                        chatId = ChatId.fromId(message.chat.id),
                        document = TelegramFile.ByFile(
                            writeCodesToFile(codesDAO.createCodes(it))
                        )
                    )
                }
            }
        }
    }.startPolling()
}

private fun Application.createBotWithCommands(commands: Dispatcher.() -> Unit): Bot =
    Bot.Builder().build {
        token = environment.config.property("bot.apiToken").getString()
        dispatch {
            commands()
        }
    }

private fun CommandHandlerEnvironment.checkCodesAmountInRange(codesAmount: Int): Boolean {
    val inRange = codesAmount in CODES_MIN_AMOUNT..CODES_MAX_AMOUNT
    if (!inRange) {
        bot.sendMessage(
            chatId = ChatId.fromId(message.chat.id),
            text = ARG_MUST_BE_IN_RANGE
        )
    }
    return inRange
}

private fun CommandHandlerEnvironment.sendMessageIfCodesAmountNull(codesAmount: Int?) {
    if (codesAmount == null) {
        bot.sendMessage(
            chatId = ChatId.fromId(message.chat.id),
            text = ONLY_NUMBERS_IN_ARG
        )
    }
}

private fun CommandHandlerEnvironment.sendMessageIfArgsBlank(joinedArgs: String) {
    joinedArgs.ifBlank {
        bot.sendMessage(
            chatId = ChatId.fromId(message.chat.id),
            text = GENERATE_CODES_WITHOUT_AMOUNT
        )
    }
}