package ru.stankin.graduation.repository.core

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Component
import ru.stankin.graduation.entity.RoleEntity_
import ru.stankin.graduation.entity.UserEntity
import ru.stankin.graduation.entity.UserEntity_
import ru.stankin.graduation.repository.UserRepository
import ru.stankin.graduation.repository.util.combineSpecification
import ru.stankin.graduation.repository.util.getLikeText

@Component
class UserRepositoryCore(
    private val userRepository: UserRepository
) {

    fun findAllByRolesInWithSearch(searchText: String?, roles: List<String>, pageable: Pageable): Page<UserEntity> {
        val nameSpecification = searchText?.let {
            Specification<UserEntity> { root, _, criteriaBuilder ->
                criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get(UserEntity_.firstName)), it.getLikeText()),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get(UserEntity_.lastName)), it.getLikeText()),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get(UserEntity_.email)), it.getLikeText())
                )
            }
        }
        val rolesSpecification = Specification<UserEntity> { root, _, _ ->
            root.join(UserEntity_.roles).get(RoleEntity_.name) .`in`(roles)
        }

        return userRepository.findAll(combineSpecification(rolesSpecification, nameSpecification), pageable)
    }

    fun save(userEntity: UserEntity): UserEntity = userRepository.save(userEntity)
    fun findByUsername(username: String): UserEntity? = userRepository.findByUsername(username)
}