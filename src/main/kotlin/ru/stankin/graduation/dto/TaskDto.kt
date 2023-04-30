package ru.stankin.graduation.dto

import java.time.ZonedDateTime

data class TaskDto(
    var id: String? = null,
    var taskTemplate: TaskTemplateDto? = null,
    var taskChecks: List<TaskCheckDto>? = null,
    var systemUserId: String? = null,
    var groupId: String? = null,
    var taskStatus: TaskStatusDto? = null,
    var createAt: ZonedDateTime? = null,
    var updateAt: ZonedDateTime? = null,
    var expireDate: ZonedDateTime? = null
) {

    enum class TaskStatusDto {
        CREATED, IN_PROGRESS, COMPLETE, EXPIRED
    }
}