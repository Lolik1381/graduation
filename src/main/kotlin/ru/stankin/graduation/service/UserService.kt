package ru.stankin.graduation.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import ru.stankin.graduation.dto.GroupDto
import ru.stankin.graduation.dto.UserDto

interface UserService {

    fun createUser(userDto: UserDto): UserDto
    fun findAll(searchText: String?, pageable: Pageable): Page<UserDto>
    fun findAllGroups(searchName: String?, pageable: Pageable): Page<GroupDto>
    fun authenticate(username: String, password: String)
    fun userRoles(): List<String>
    fun getUserByContext(): UserDto
}