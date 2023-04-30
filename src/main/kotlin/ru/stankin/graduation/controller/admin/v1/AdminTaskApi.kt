package ru.stankin.graduation.controller.admin.v1

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import ru.stankin.graduation.dto.CommonResponse
import ru.stankin.graduation.dto.TaskDto

@RequestMapping("/admin/v1/task")
interface AdminTaskApi {

    @PostMapping("/create")
    fun create(@RequestBody taskDto: TaskDto): CommonResponse<TaskDto>
}