package ru.stankin.graduation.controller.admin.v1

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import ru.stankin.graduation.dto.CommonResponse
import ru.stankin.graduation.dto.TaskSchedulerDto

@RequestMapping("/admin/v1/task/scheduler")
interface AdminTaskSchedulerApi {

    @PostMapping("/create")
    fun create(@RequestBody taskSchedulerDto: TaskSchedulerDto): CommonResponse<TaskSchedulerDto>

    @PutMapping("/update/{id}")
    fun update(@PathVariable id: String, @RequestParam cron: String): CommonResponse<TaskSchedulerDto>

    @PutMapping("/delete/{id}")
    fun delete(@PathVariable id: String)
}