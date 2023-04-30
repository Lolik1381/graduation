package ru.stankin.graduation.exception

interface ErrorInfo {
    val messageFormat: String

    companion object {

        fun buildErrorInfo(messageFormat: String?): ErrorInfo {
            return object : ErrorInfo {
                override val messageFormat = messageFormat ?: ""
            }
        }
    }
}