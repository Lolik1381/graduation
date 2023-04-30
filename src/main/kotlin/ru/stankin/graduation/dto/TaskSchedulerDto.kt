package ru.stankin.graduation.dto

import java.time.ZonedDateTime

data class TaskSchedulerDto(
    var id: String? = null,
    var taskTemplate: TaskTemplateDto? = null,
    var type: TaskSchedulerTypeDto? = null,
    var cron: String? = null,
    var triggerDate: ZonedDateTime? = null,
    var status: TaskSchedulerStatusDto? = null,
    var userId: String? = null,
    var groupId: String? = null,
    var expireDelay: Long? = null
) {

    enum class TaskSchedulerTypeDto {
        CRON, DATE
    }

    enum class TaskSchedulerStatusDto {
        ACTIVE, DELETED
    }
}