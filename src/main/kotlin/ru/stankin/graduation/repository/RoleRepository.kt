package ru.stankin.graduation.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.stankin.graduation.entity.RoleEntity

interface RoleRepository : JpaRepository<RoleEntity, String> {

    fun findByName(name: String): RoleEntity?
}