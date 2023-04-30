package ru.stankin.graduation.service

import ru.stankin.graduation.dto.TaskSchedulerDto

interface TaskSchedulerService {

    fun create(taskSchedulerDto: TaskSchedulerDto): TaskSchedulerDto
    fun update(id: String, cron: String): TaskSchedulerDto
    fun delete(id: String)
}