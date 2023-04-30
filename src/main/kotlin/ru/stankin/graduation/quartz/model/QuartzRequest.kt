package ru.stankin.graduation.quartz.model

import java.time.ZonedDateTime
import org.quartz.Job
import ru.stankin.graduation.entity.TaskSchedulerEntity

data class QuartzRequest(
    val id: String,
    var data: Map<String, Any> = emptyMap(),
    val jobName: JobName,
    val jobGroup: JobGroup,
    val type: JobType,
    val cronExpression: String? = null,
    val triggerDate: ZonedDateTime? = null,
    val jobClass: Class<out Job>
) {

    enum class JobName(
        val prefixJobName: String,
        val prefixTriggerName: String
    ) {
        TASK_CREATE("ru.stankin.graduation.task.create", "ru.stankin.graduation.task.create.taskCreateTrigger")
    }

    enum class JobGroup(
        val jobName: String,
        val groupTriggerName: String
    ) {
        TASK_CREATE_GROUP("ru.stankin.graduation.group.task.create", "ru.stankin.graduation.task.create.taskCreateGroupTrigger")
    }

    enum class JobType {
        CRON, SIMPLE_TRIGGER
    }
}

fun toJobType(type: TaskSchedulerEntity.TaskSchedulerType): QuartzRequest.JobType {
    return when (type) {
        TaskSchedulerEntity.TaskSchedulerType.CRON -> QuartzRequest.JobType.CRON
        TaskSchedulerEntity.TaskSchedulerType.DATE -> QuartzRequest.JobType.SIMPLE_TRIGGER
    }
}