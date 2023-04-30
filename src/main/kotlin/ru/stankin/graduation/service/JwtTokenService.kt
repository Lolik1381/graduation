package ru.stankin.graduation.service

import io.jsonwebtoken.Claims
import java.time.ZonedDateTime
import org.springframework.security.core.userdetails.UserDetails

interface JwtTokenService {

    fun getUsernameFromJwtToken(jwtToken: String): String
    fun getExpirationDateFromJwtToken(jwtToken: String): ZonedDateTime
    fun <T> getClaimsFromJwtToken(jwtToken: String, claimsResolver: (Claims) -> T): T
    fun validateJwtToken(jwtToken: String, userDetails: UserDetails): Boolean
    fun generateToken(userDetails: UserDetails): String
}