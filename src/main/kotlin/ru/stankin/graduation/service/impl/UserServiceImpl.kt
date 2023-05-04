package ru.stankin.graduation.service.impl

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.authentication.AccountExpiredException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import ru.stankin.graduation.dto.GroupDto
import ru.stankin.graduation.dto.RequestChangePasswordDto
import ru.stankin.graduation.dto.RequestLoginDto
import ru.stankin.graduation.dto.RequestUserDto
import ru.stankin.graduation.dto.ResponseLoginDto
import ru.stankin.graduation.dto.ResponseUserDto
import ru.stankin.graduation.dto.RoleDto
import ru.stankin.graduation.exception.ApplicationException
import ru.stankin.graduation.exception.BusinessErrorInfo
import ru.stankin.graduation.exception.ErrorInfo
import ru.stankin.graduation.mapper.UserMapper
import ru.stankin.graduation.repository.GroupRepository
import ru.stankin.graduation.repository.RoleRepository
import ru.stankin.graduation.repository.core.UserRepositoryCore
import ru.stankin.graduation.repository.util.getLikeText
import ru.stankin.graduation.service.JwtTokenService
import ru.stankin.graduation.service.UserService

@Service
class UserServiceImpl(
    private val userRepository: UserRepositoryCore,
    private val roleRepository: RoleRepository,
    private val groupRepository: GroupRepository,
    private val passwordEncoder: PasswordEncoder,
    private val authenticationManager: AuthenticationManager,
    private val userMapper: UserMapper,
    private val userDetailsService: UserDetailsService,
    private val jwtTokenService: JwtTokenService
) : UserService {

    override fun login(requestLoginDto: RequestLoginDto): ResponseLoginDto {
        val username = checkNotNull(requestLoginDto.login)
        val password = checkNotNull(requestLoginDto.password)

        try {
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username, password))
        } catch (e: DisabledException) {
            throw ApplicationException(BusinessErrorInfo.USER_DISABLED)
        } catch (e: BadCredentialsException) {
            throw ApplicationException(BusinessErrorInfo.USER_BAD_CREDENTIALS)
        } catch (e: AccountExpiredException) {
            throw ApplicationException(BusinessErrorInfo.USER_ACCOUNT_EXPIRED)
        } catch (e: InternalAuthenticationServiceException) {
            throw ApplicationException(ErrorInfo.buildErrorInfo(e.message))
        }

        val userEntity = userRepository.findByUsername(username)
            ?: throw ApplicationException(BusinessErrorInfo.USER_BY_USERNAME_NOT_FOUND, username)
        val userDetails = userDetailsService.loadUserByUsername(userEntity.username)

        return ResponseLoginDto(
            token = jwtTokenService.generateToken(userDetails),
            roles = userDetails.authorities
                .filter { it.authority.startsWith("ROLE_") }
                .map { it.authority.replace("ROLE_", "") },
            username = username,
            firstName = userEntity.firstName,
            lastName = userEntity.lastName,
            email = userEntity.email,
            isTemporaryPassword = userEntity.isTemporaryPassword
        )
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    override fun changePassword(requestChangePasswordDto: RequestChangePasswordDto): ResponseUserDto {
        val username = checkNotNull(requestChangePasswordDto.login)
        val oldPassword = checkNotNull(requestChangePasswordDto.oldPassword)
        val newPassword = checkNotNull(requestChangePasswordDto.newPassword)

        val userEntity = userRepository.findByUsername(username)
            ?: throw ApplicationException(BusinessErrorInfo.USER_BY_USERNAME_NOT_FOUND, username)

        if (!passwordEncoder.matches(oldPassword, userEntity.password)) {
            throw ApplicationException(BusinessErrorInfo.USER_OLD_PASSWORD_NOT_VALID)
        }

        userEntity.password = passwordEncoder.encode(newPassword)
        userEntity.isTemporaryPassword = false

        return userMapper.toDto(userRepository.save(userEntity))
    }

    @Transactional
    override fun createUser(requestUserDto: RequestUserDto): ResponseUserDto {
        requestUserDto.password = passwordEncoder.encode(requestUserDto.password)

        val roles = roleRepository.findAllById(listOf(RoleDto.RoleDtoName.USER.name))

        return userMapper.toDto(userRepository.save(userMapper.toEntity(requestUserDto, roles)))
    }

    @Transactional(readOnly = true)
    override fun findAll(searchText: String?, pageable: Pageable): Page<ResponseUserDto> {
        return userRepository.findAllByRolesInWithSearch(searchText, listOf(RoleDto.RoleDtoName.USER.name), pageable)
            .map { userMapper.toDto(it) }
    }

    override fun findAllGroups(searchName: String?, pageable: Pageable): Page<GroupDto> {
        return groupRepository.findAllByNameContaining(searchName.getLikeText(), pageable)
            .map { GroupDto(it.id, it.name) }
    }

    override fun getUserByContext(): ResponseUserDto {
        val username = (SecurityContextHolder.getContext().authentication.principal as User).username

        val userEntity = userRepository.findByUsername(username)
            ?: throw ApplicationException(BusinessErrorInfo.USER_BY_USERNAME_NOT_FOUND, username)

        return userMapper.toDto(userEntity)
    }
}