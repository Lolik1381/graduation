package ru.stankin.graduation.dto

data class RoleDto(

    var id: String? = null,
    var name: RoleDtoName? = null
) {

    enum class RoleDtoName {
        ADMIN, USER
    }
}