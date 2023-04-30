package ru.stankin.graduation.controller.user.v1

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import ru.stankin.graduation.dto.CommonResponse
import ru.stankin.graduation.dto.RequestLoginDto

@RequestMapping("/user/v1")
interface UserApi {

    @PostMapping("/login")
    fun login(@RequestBody loginDto: RequestLoginDto): CommonResponse<String>

    @GetMapping("/roles")
    fun userRoles(): CommonResponse<List<String>>
}