package ru.stankin.graduation.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.stankin.graduation.entity.TaskTemplateCheckEntity

interface TaskTemplateCheckRepository : JpaRepository<TaskTemplateCheckEntity, String> {

    fun findByIdAndTaskTemplateId(id: String, taskTemplateId: String): TaskTemplateCheckEntity?
    fun findAllByTaskTemplateId(taskTemplateId: String): List<TaskTemplateCheckEntity>
    fun deleteAllByIdNotInAndTaskTemplateId(id: List<String>, taskTemplateId: String)
}