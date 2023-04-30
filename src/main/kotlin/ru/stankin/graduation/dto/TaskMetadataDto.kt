package ru.stankin.graduation.dto

import java.time.ZonedDateTime

data class TaskMetadataDto(
    var id: String? = null,
    var header: String? = null,
    var description: String? = null,
    var status: TaskDto.TaskStatusDto? = null,
    var createAt: ZonedDateTime? = null,
    var updateAt: ZonedDateTime? = null
)