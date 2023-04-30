package ru.stankin.graduation.service.impl

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.stankin.graduation.dto.GroupDto
import ru.stankin.graduation.dto.RoleDto
import ru.stankin.graduation.dto.UserDto
import ru.stankin.graduation.exception.ApplicationException
import ru.stankin.graduation.exception.BusinessErrorInfo
import ru.stankin.graduation.exception.ErrorInfo
import ru.stankin.graduation.mapper.UserMapper
import ru.stankin.graduation.repository.GroupRepository
import ru.stankin.graduation.repository.RoleRepository
import ru.stankin.graduation.repository.core.UserRepositoryCore
import ru.stankin.graduation.repository.util.getLikeText
import ru.stankin.graduation.service.UserService

@Service
class UserServiceImpl(
    private val userRepository: UserRepositoryCore,
    private val roleRepository: RoleRepository,
    private val groupRepository: GroupRepository,
    private val passwordEncoder: PasswordEncoder,
    private val authenticationManager: AuthenticationManager,
    private val userMapper: UserMapper
) : UserService {

    @Transactional
    override fun createUser(userDto: UserDto): UserDto {
        userDto.password = passwordEncoder.encode(userDto.password)

        val roles = roleRepository.findAllById(listOf(RoleDto.RoleDtoName.USER.name))

        return userMapper.toDto(userRepository.save(userMapper.toEntity(userDto, roles)))
    }

    @Transactional(readOnly = true)
    override fun findAll(searchText: String?, pageable: Pageable): Page<UserDto> {
        return userRepository.findAllByRolesInWithSearch(searchText, listOf(RoleDto.RoleDtoName.USER.name), pageable)
            .map { userMapper.toDto(it) }
    }

    override fun findAllGroups(searchName: String?, pageable: Pageable): Page<GroupDto> {
        return groupRepository.findAllByNameContaining(searchName.getLikeText(), pageable)
            .map { GroupDto(it.id, it.name) }
    }

    override fun authenticate(username: String, password: String) {
        try {
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username, password))
        } catch (e: DisabledException) {
            throw ApplicationException(BusinessErrorInfo.USER_DISABLED)
        } catch (e: BadCredentialsException) {
            throw ApplicationException(BusinessErrorInfo.USER_BAD_CREDENTIALS)
        } catch (e: InternalAuthenticationServiceException) {
            throw ApplicationException(ErrorInfo.buildErrorInfo(e.message))
        }
    }

    override fun userRoles(): List<String> {
        val user = SecurityContextHolder.getContext()
            .authentication
            .principal as User

        return user.authorities
            .filter { it.authority.startsWith("ROLE_") }
            .map { it.authority.replace("ROLE_", "") }
    }

    override fun getUserByContext(): UserDto {
        val username = (SecurityContextHolder.getContext().authentication.principal as User).username

        val userEntity = userRepository.findByUsername(username)
            ?: throw ApplicationException(BusinessErrorInfo.USER_BY_USERNAME_NOT_FOUND, username)

        return userMapper.toDto(userEntity)
    }
}