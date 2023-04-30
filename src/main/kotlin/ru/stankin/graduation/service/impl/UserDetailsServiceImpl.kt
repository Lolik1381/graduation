package ru.stankin.graduation.service.impl

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.stankin.graduation.exception.ApplicationException
import ru.stankin.graduation.exception.BusinessErrorInfo
import ru.stankin.graduation.repository.UserRepository

@Service
class UserDetailsServiceImpl(
    private val userRepository: UserRepository
) : UserDetailsService {

    @Transactional(readOnly = true)
    override fun loadUserByUsername(username: String): UserDetails {
        val userEntity = userRepository.findByUsername(username)
            ?: throw ApplicationException(BusinessErrorInfo.USER_BY_USERNAME_NOT_FOUND, username)

        val authorities = userEntity.roles?.map { SimpleGrantedAuthority("ROLE_${it.name}") } ?: emptyList()

        return User.builder()
            .authorities(authorities)
            .username(userEntity.username)
            .password(userEntity.password)
            .build()
    }
}