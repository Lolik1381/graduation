package ru.stankin.graduation.dto

data class ResponseLoginDto(
    var token: String? = null,
    var roles: List<String>? = null,
    var username: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var email: String? = null,
    var isTemporaryPassword: Boolean = false
)