package ru.stankin.graduation.controller.user.v1

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import ru.stankin.graduation.dto.CommonResponse
import ru.stankin.graduation.dto.RequestChangePasswordDto
import ru.stankin.graduation.dto.RequestLoginDto
import ru.stankin.graduation.dto.ResponseLoginDto

@RequestMapping("/user/v1")
interface UserApi {

    @PostMapping("/login")
    fun login(@RequestBody loginDto: RequestLoginDto): CommonResponse<ResponseLoginDto>

    @PutMapping("/changePassword")
    fun changePassword(@RequestBody changePasswordDto: RequestChangePasswordDto): CommonResponse<Unit>
}