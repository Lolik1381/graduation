package ru.stankin.graduation.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import ru.stankin.graduation.dto.TaskTemplateDto

interface TaskTemplateService {

    fun create(taskTemplateDto: TaskTemplateDto): TaskTemplateDto
    fun updateTaskTemplate(id: String, taskTemplateDto: TaskTemplateDto): TaskTemplateDto
    fun activeTaskTemplate(id: String): TaskTemplateDto
    fun deleteTaskTemplate(id: String): TaskTemplateDto
    fun findAll(searchText: String?, pageable: Pageable): Page<TaskTemplateDto>
    fun findById(id: String): TaskTemplateDto
}