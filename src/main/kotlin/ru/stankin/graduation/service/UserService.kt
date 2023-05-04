package ru.stankin.graduation.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import ru.stankin.graduation.dto.GroupDto
import ru.stankin.graduation.dto.RequestChangePasswordDto
import ru.stankin.graduation.dto.RequestLoginDto
import ru.stankin.graduation.dto.RequestUserDto
import ru.stankin.graduation.dto.ResponseLoginDto
import ru.stankin.graduation.dto.ResponseUserDto

interface UserService {

    fun login(requestLoginDto: RequestLoginDto): ResponseLoginDto
    fun changePassword(requestChangePasswordDto: RequestChangePasswordDto): ResponseUserDto
    fun createUser(requestUserDto: RequestUserDto): ResponseUserDto
    fun findAll(searchText: String?, pageable: Pageable): Page<ResponseUserDto>
    fun findAllGroups(searchName: String?, pageable: Pageable): Page<GroupDto>
    fun getUserByContext(): ResponseUserDto
}