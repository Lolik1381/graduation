package ru.stankin.graduation.controller.admin.v1.impl

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.RestController
import ru.stankin.graduation.controller.admin.v1.AdminTaskTemplateApi
import ru.stankin.graduation.dto.CommonResponse
import ru.stankin.graduation.dto.TaskTemplateDto
import ru.stankin.graduation.service.TaskTemplateService

@RestController
class AdminTaskTemplateController(
    private val taskTemplateService: TaskTemplateService
) : AdminTaskTemplateApi {

    override fun createTaskTemplate(taskTemplateDto: TaskTemplateDto): CommonResponse<TaskTemplateDto> {
        return CommonResponse.ok(taskTemplateService.create(taskTemplateDto))
    }

    override fun updateTaskTemplate(id: String, taskTemplateDto: TaskTemplateDto): CommonResponse<TaskTemplateDto> {
        return CommonResponse.ok(taskTemplateService.updateTaskTemplate(id, taskTemplateDto))
    }

    override fun activeTaskTemplate(id: String): CommonResponse<TaskTemplateDto> {
        return CommonResponse.ok(taskTemplateService.activeTaskTemplate(id))
    }

    override fun deleteTaskTemplate(id: String): CommonResponse<TaskTemplateDto> {
        return CommonResponse.ok(taskTemplateService.deleteTaskTemplate(id))
    }

    override fun findAll(searchText: String?, pageable: Pageable): CommonResponse<Page<TaskTemplateDto>> {
        return CommonResponse.ok(taskTemplateService.findAll(searchText, pageable))
    }

    override fun findById(id: String): CommonResponse<TaskTemplateDto> {
        return CommonResponse.ok(taskTemplateService.findById(id))
    }
}