package ru.stankin.graduation.service.impl

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.Date
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import ru.stankin.graduation.config.properties.SecurityProperty
import ru.stankin.graduation.service.JwtTokenService

@Service
class JwtTokenServiceImpl(
    private val securityProperty: SecurityProperty
) : JwtTokenService {

    override fun getUsernameFromJwtToken(jwtToken: String): String {
        return getClaimsFromJwtToken(jwtToken, Claims::getSubject)
    }

    override fun getExpirationDateFromJwtToken(jwtToken: String): ZonedDateTime {
        return getClaimsFromJwtToken(jwtToken, Claims::getExpiration)
            .toInstant()
            .atZone(ZoneId.systemDefault())
    }

    override fun <T> getClaimsFromJwtToken(jwtToken: String, claimsResolver: (Claims) -> T): T {
        val claims = getAllClaimsFromJwtToken(jwtToken)
        return claimsResolver.invoke(claims)
    }

    override fun validateJwtToken(jwtToken: String, userDetails: UserDetails): Boolean {
        return getUsernameFromJwtToken(jwtToken) == userDetails.username && !isJwtTokenExpired(jwtToken)
    }

    override fun generateToken(userDetails: UserDetails): String {
        val timeMillis = System.currentTimeMillis()
        val currentDate = Date(timeMillis)
        val expiredDate = Date(timeMillis + securityProperty.passwordLifetimeMs!! * 1000)

        return Jwts.builder()
            .claim("roles", userDetails.authorities.map { it.authority })
            .setSubject(userDetails.username)
            .setIssuedAt(currentDate)
            .setExpiration(expiredDate)
            .signWith(SignatureAlgorithm.HS512, securityProperty.jwtSecret)
            .compact()
    }

    private fun getAllClaimsFromJwtToken(jwtToken: String): Claims {
        return Jwts.parser()
            .setSigningKey(securityProperty.jwtSecret)
            .parseClaimsJws(jwtToken)
            .body
    }

    private fun isJwtTokenExpired(jwtToken: String): Boolean {
        return getExpirationDateFromJwtToken(jwtToken)
            .isBefore(ZonedDateTime.now())
    }
}