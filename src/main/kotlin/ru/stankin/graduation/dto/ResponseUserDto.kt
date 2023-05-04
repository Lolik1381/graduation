package ru.stankin.graduation.dto

data class ResponseUserDto(

    var id: String? = null,
    var username: String? = null,
    var isTemporaryPassword: Boolean = true,
    var firstName: String? = null,
    var lastName: String? = null,
    var email: String? = null
)