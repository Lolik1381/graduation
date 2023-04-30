package ru.stankin.graduation.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import ru.stankin.graduation.entity.TaskTemplateEntity

interface TaskTemplateRepository : JpaRepository<TaskTemplateEntity, String> {

    fun findByIdAndStatus(id: String?, status: TaskTemplateEntity.TaskTemplateStatus): TaskTemplateEntity?

    @Query("select tte from TaskTemplateEntity tte where lower(tte.header) like :header")
    fun findAllByHeaderLike(header: String, pageable: Pageable): Page<TaskTemplateEntity>
}