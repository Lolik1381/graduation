package ru.stankin.graduation.controller.admin.v1.impl

import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import ru.stankin.graduation.controller.admin.v1.AdminTaskApi
import ru.stankin.graduation.dto.CommonResponse
import ru.stankin.graduation.dto.TaskDto
import ru.stankin.graduation.service.TaskService

@RestController
class AdminTaskController(
    private val taskService: TaskService
) : AdminTaskApi {

    override fun create(@RequestBody taskDto: TaskDto): CommonResponse<TaskDto> {
        return CommonResponse.ok(taskService.create(taskDto))
    }
}