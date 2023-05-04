package ru.stankin.graduation.controller.admin.v1

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import ru.stankin.graduation.dto.CommonResponse
import ru.stankin.graduation.dto.GroupDto
import ru.stankin.graduation.dto.RequestUserDto
import ru.stankin.graduation.dto.ResponseUserDto

@RequestMapping("/admin/v1/user")
interface AdminUserApi {

    @PostMapping("/create")
    fun create(@RequestBody requestUserDto: RequestUserDto): CommonResponse<ResponseUserDto>

    @GetMapping("/findAll")
    fun findAll(@RequestParam(required = false) searchText: String?, pageable: Pageable): CommonResponse<Page<ResponseUserDto>>

    @GetMapping("/groups")
    fun findAllGroups(@RequestParam(required = false) searchName: String?, pageable: Pageable): CommonResponse<Page<GroupDto>>
}