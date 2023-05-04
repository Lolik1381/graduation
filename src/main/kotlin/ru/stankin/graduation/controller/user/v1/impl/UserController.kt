package ru.stankin.graduation.controller.user.v1.impl

import org.springframework.web.bind.annotation.RestController
import ru.stankin.graduation.controller.user.v1.UserApi
import ru.stankin.graduation.dto.CommonResponse
import ru.stankin.graduation.dto.RequestChangePasswordDto
import ru.stankin.graduation.dto.RequestLoginDto
import ru.stankin.graduation.dto.ResponseLoginDto
import ru.stankin.graduation.service.UserService

@RestController
class UserController(
    private val userService: UserService
): UserApi {

    override fun login(loginDto: RequestLoginDto): CommonResponse<ResponseLoginDto> {
        return CommonResponse.ok(userService.login(loginDto))
    }

    override fun changePassword(changePasswordDto: RequestChangePasswordDto): CommonResponse<Unit> {
        userService.changePassword(changePasswordDto)
        return CommonResponse.ok()
    }
}