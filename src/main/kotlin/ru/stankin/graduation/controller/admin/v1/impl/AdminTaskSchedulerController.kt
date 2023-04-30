package ru.stankin.graduation.controller.admin.v1.impl

import org.springframework.web.bind.annotation.RestController
import ru.stankin.graduation.controller.admin.v1.AdminTaskSchedulerApi
import ru.stankin.graduation.dto.CommonResponse
import ru.stankin.graduation.dto.TaskSchedulerDto
import ru.stankin.graduation.service.TaskSchedulerService

@RestController
class AdminTaskSchedulerController(
    private val taskSchedulerService: TaskSchedulerService
) : AdminTaskSchedulerApi {

    override fun create(taskSchedulerDto: TaskSchedulerDto): CommonResponse<TaskSchedulerDto> {
        return CommonResponse.ok(taskSchedulerService.create(taskSchedulerDto))
    }

    override fun update(id: String, cron: String): CommonResponse<TaskSchedulerDto> {
        return CommonResponse.ok(taskSchedulerService.update(id, cron))
    }

    override fun delete(id: String) {
        return taskSchedulerService.delete(id)
    }
}