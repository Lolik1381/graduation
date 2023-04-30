package ru.stankin.graduation.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import ru.stankin.graduation.entity.GroupEntity

interface GroupRepository : JpaRepository<GroupEntity, String> {

    @Query("select ge from GroupEntity ge where lower(ge.name) like :name")
    fun findAllByNameContaining(name: String, pageable: Pageable): Page<GroupEntity>
}