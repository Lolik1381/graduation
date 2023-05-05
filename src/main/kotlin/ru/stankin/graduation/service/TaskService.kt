package ru.stankin.graduation.service

import ru.stankin.graduation.dto.TaskDto
import ru.stankin.graduation.dto.TaskMetadataDto

interface TaskService {

    fun findTaskById(taskId: String): TaskDto
    fun findTasksMetadata(status: TaskDto.TaskStatusDto?, searchText: String?, equipmentId: String?): List<TaskMetadataDto>
    fun create(taskDto: TaskDto): TaskDto
    fun progress(id: String): TaskDto
    fun complete(id: String): TaskDto
}