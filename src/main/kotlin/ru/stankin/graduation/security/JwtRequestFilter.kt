package ru.stankin.graduation.security

import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import ru.stankin.graduation.service.JwtTokenService

@Component
class JwtRequestFilter(
    private val jwtTokenService: JwtTokenService,
    private val userDetailsService: UserDetailsService
) : OncePerRequestFilter() {

    companion object {
        private const val HEADER_NAME = "Authorization"
        private const val TOKEN_TYPE = "Bearer "
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val requestJwtTokenHeader = request.getHeader(HEADER_NAME)
        val jwtToken = getJwtToken(requestJwtTokenHeader)
        val username = jwtToken?.run { jwtTokenService.getUsernameFromJwtToken(this) }

        if (username != null && SecurityContextHolder.getContext().authentication == null) {
            val userDetails = userDetailsService.loadUserByUsername(username)

            if (jwtTokenService.validateJwtToken(jwtToken, userDetails)) {
                val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                usernamePasswordAuthenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)

                SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
            }
        }

        filterChain.doFilter(request, response)
    }

    private fun getJwtToken(requestJwtTokenHeader: String?): String? {
        return when {
            requestJwtTokenHeader != null && requestJwtTokenHeader.startsWith(TOKEN_TYPE) -> requestJwtTokenHeader.substring(7)
            else -> null
        }
    }
}