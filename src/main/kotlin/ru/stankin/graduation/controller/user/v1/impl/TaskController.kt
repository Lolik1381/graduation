package ru.stankin.graduation.controller.user.v1.impl

import org.springframework.web.bind.annotation.RestController
import ru.stankin.graduation.dto.CommonResponse
import ru.stankin.graduation.dto.TaskDto
import ru.stankin.graduation.dto.TaskMetadataDto
import ru.stankin.graduation.controller.user.v1.TaskApi
import ru.stankin.graduation.service.TaskService

@RestController
class TaskController(
    private val taskService: TaskService
) : TaskApi {

    override fun findTaskById(taskId: String): CommonResponse<TaskDto> {
        return CommonResponse.ok(taskService.findTaskById(taskId))
    }

    override fun findTasks(status: TaskDto.TaskStatusDto?, searchText: String?, equipmentId: String?): CommonResponse<List<TaskMetadataDto>> {
        return CommonResponse.ok(taskService.findTasksMetadata(status, searchText, equipmentId))
    }
}