package ru.stankin.graduation.controller.user.v1.impl

import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.web.bind.annotation.RestController
import ru.stankin.graduation.dto.CommonResponse
import ru.stankin.graduation.dto.RequestLoginDto
import ru.stankin.graduation.controller.user.v1.UserApi
import ru.stankin.graduation.service.JwtTokenService
import ru.stankin.graduation.service.UserService

@RestController
class UserController(
    private val userService: UserService,
    private val userDetailsService: UserDetailsService,
    private val jwtTokenService: JwtTokenService
) : UserApi {

    override fun login(loginDto: RequestLoginDto): CommonResponse<String> {
        userService.authenticate(loginDto.login!!, loginDto.password!!)

        return CommonResponse.ok(jwtTokenService.generateToken(userDetailsService.loadUserByUsername(loginDto.login)))
    }

    override fun userRoles(): CommonResponse<List<String>> {
        return CommonResponse.ok(userService.userRoles())
    }
}