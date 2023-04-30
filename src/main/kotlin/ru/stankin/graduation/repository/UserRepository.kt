package ru.stankin.graduation.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import ru.stankin.graduation.entity.UserEntity

interface UserRepository : JpaRepository<UserEntity, String>, JpaSpecificationExecutor<UserEntity> {

    @Query("""
        select u 
            from UserEntity u 
        join u.roles r
        where r.name in :roles
    """)
    fun findAllByRolesIn(roles: List<String>, pageable: Pageable): Page<UserEntity>

    fun findByUsername(username: String): UserEntity?
}