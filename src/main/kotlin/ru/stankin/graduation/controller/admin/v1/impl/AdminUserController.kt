package ru.stankin.graduation.controller.admin.v1.impl

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import ru.stankin.graduation.controller.admin.v1.AdminUserApi
import ru.stankin.graduation.dto.CommonResponse
import ru.stankin.graduation.dto.GroupDto
import ru.stankin.graduation.dto.RequestUserDto
import ru.stankin.graduation.dto.ResponseUserDto
import ru.stankin.graduation.service.UserService

@RestController
class AdminUserController(
    private val userService: UserService
) : AdminUserApi {

    override fun create(@RequestBody requestUserDto: RequestUserDto): CommonResponse<ResponseUserDto> {
        return CommonResponse.ok(userService.createUser(requestUserDto))
    }

    override fun findAll(searchText: String?, pageable: Pageable): CommonResponse<Page<ResponseUserDto>> {
        return CommonResponse.ok(userService.findAll(searchText, pageable))
    }

    override fun findAllGroups(searchName: String?, pageable: Pageable): CommonResponse<Page<GroupDto>> {
        return CommonResponse.ok(userService.findAllGroups(searchName, pageable))
    }
}