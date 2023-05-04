package ru.stankin.graduation.dto

data class RequestChangePasswordDto(
    var login: String? = null,
    var oldPassword: String? = null,
    var newPassword: String? = null
)