package ru.stankin.graduation.dto

data class RequestUserDto(

    var id: String? = null,
    var username: String? = null,
    var password: String? = null,
    var isTemporaryPassword: Boolean = true,
    var firstName: String? = null,
    var lastName: String? = null,
    var email: String? = null
)