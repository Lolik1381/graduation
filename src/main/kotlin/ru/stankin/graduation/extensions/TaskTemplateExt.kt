package ru.stankin.graduation.extensions

import ru.stankin.graduation.exception.ApplicationException
import ru.stankin.graduation.exception.ErrorInfo

fun String?.checkRequiredField(isRequired: Boolean?, errorInfo: ErrorInfo, vararg params: String?) {
    if (isRequired == true && this.isNullOrBlank()) {
        throw ApplicationException(errorInfo, *params)
    }
}