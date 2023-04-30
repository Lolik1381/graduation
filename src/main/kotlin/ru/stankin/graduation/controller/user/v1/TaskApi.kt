package ru.stankin.graduation.controller.user.v1

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import ru.stankin.graduation.dto.CommonResponse
import ru.stankin.graduation.dto.TaskDto
import ru.stankin.graduation.dto.TaskMetadataDto

@RequestMapping("/user/v1/task")
interface TaskApi{

    @GetMapping("/find/{taskId}")
    fun findTaskById(@PathVariable taskId: String): CommonResponse<TaskDto>

    @GetMapping("/findall")
    fun findTasks(@RequestParam(required = false) status: TaskDto.TaskStatusDto?): CommonResponse<List<TaskMetadataDto>>
}