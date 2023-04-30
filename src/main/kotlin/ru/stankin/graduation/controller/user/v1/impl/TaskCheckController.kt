package ru.stankin.graduation.controller.user.v1.impl

import org.springframework.web.bind.annotation.RestController
import ru.stankin.graduation.controller.user.v1.TaskCheckApi
import ru.stankin.graduation.dto.CommonResponse
import ru.stankin.graduation.dto.TaskCheckDto
import ru.stankin.graduation.service.TaskCheckService

@RestController
class TaskCheckController(
    private val taskCheckService: TaskCheckService
) : TaskCheckApi {

    override fun update(
        id: String,
        comment: String?,
        controlValue: String?,
        photoIds: List<String>?
    ): CommonResponse<TaskCheckDto> {
        return CommonResponse.ok(taskCheckService.update(id, comment, controlValue, photoIds))
    }
}