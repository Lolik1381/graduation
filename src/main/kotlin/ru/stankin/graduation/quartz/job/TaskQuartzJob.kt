package ru.stankin.graduation.quartz.job

import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import org.quartz.JobExecutionContext
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.scheduling.quartz.QuartzJobBean
import org.springframework.stereotype.Component
import org.springframework.transaction.support.TransactionTemplate
import ru.stankin.graduation.dto.TaskDto
import ru.stankin.graduation.entity.TaskSchedulerEntity
import ru.stankin.graduation.exception.ApplicationException
import ru.stankin.graduation.exception.BusinessErrorInfo
import ru.stankin.graduation.mapper.TaskTemplateMapper
import ru.stankin.graduation.repository.TaskSchedulerRepository
import ru.stankin.graduation.service.TaskService

@Component
class TaskQuartzJob: QuartzJobBean() {

    @Autowired
    private lateinit var taskSchedulerRepository: TaskSchedulerRepository

    @Autowired
    private lateinit var taskService: TaskService

    @Autowired
    private lateinit var taskTemplateMapper: TaskTemplateMapper

    @Autowired
    private lateinit var transactionTemplate: TransactionTemplate

    companion object {
        private val logger = LoggerFactory.getLogger(TaskQuartzJob::class.java)
    }

    override fun executeInternal(context: JobExecutionContext) {
        logger.info("Execute TaskQuartzJob")

        transactionTemplate.execute {
            val id = context.jobDetail.jobDataMap["id"] as String
            val taskSchedulerEntity = taskSchedulerRepository.findByIdOrNull(id)
                ?: throw ApplicationException(BusinessErrorInfo.TASK_SCHEDULER_NOT_FOUND)

            if (taskSchedulerEntity.type == TaskSchedulerEntity.TaskSchedulerType.DATE) {
                taskSchedulerEntity.status = TaskSchedulerEntity.TaskSchedulerStatus.DELETED
            }

            val taskDto = TaskDto(
                taskTemplate = taskTemplateMapper.toDto(taskSchedulerEntity.taskTemplate!!),
                systemUserId = taskSchedulerEntity.userId,
                groupId = taskSchedulerEntity.groupId,
                expireDate = ZonedDateTime.now().plus(taskSchedulerEntity.expireDelay!!, ChronoUnit.MILLIS)
            )
            taskService.create(taskDto)

            taskSchedulerRepository.save(taskSchedulerEntity)
        }
    }
}