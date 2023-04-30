package ru.stankin.graduation.exception

class ApplicationException(errorInfo: ErrorInfo, vararg params: String?) : RuntimeException(String.format(errorInfo.messageFormat, *params)) {
}