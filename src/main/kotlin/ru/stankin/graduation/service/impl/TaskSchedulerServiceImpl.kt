package ru.stankin.graduation.service.impl

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.stankin.graduation.dto.TaskSchedulerDto
import ru.stankin.graduation.entity.TaskSchedulerEntity
import ru.stankin.graduation.entity.TaskTemplateEntity
import ru.stankin.graduation.exception.ApplicationException
import ru.stankin.graduation.exception.BusinessErrorInfo
import ru.stankin.graduation.mapper.TaskSchedulerMapper
import ru.stankin.graduation.quartz.job.TaskQuartzJob
import ru.stankin.graduation.quartz.model.QuartzRequest
import ru.stankin.graduation.quartz.model.toJobType
import ru.stankin.graduation.quartz.service.QuartzSchedulerService
import ru.stankin.graduation.repository.TaskSchedulerRepository
import ru.stankin.graduation.repository.TaskTemplateRepository
import ru.stankin.graduation.service.TaskSchedulerService

@Service
class TaskSchedulerServiceImpl(
    private val taskSchedulerMapper: TaskSchedulerMapper,
    private val taskTemplateRepository: TaskTemplateRepository,
    private val taskSchedulerRepository: TaskSchedulerRepository,
    private val quartzSchedulerService: QuartzSchedulerService
) : TaskSchedulerService {

    @Transactional
    override fun create(taskSchedulerDto: TaskSchedulerDto): TaskSchedulerDto {
        val taskTemplate = taskTemplateRepository.findByIdAndStatus(taskSchedulerDto.taskTemplate?.id, TaskTemplateEntity.TaskTemplateStatus.ACTIVE)
            ?: throw ApplicationException(BusinessErrorInfo.TASK_TEMPLATE_NOT_FOUND, taskSchedulerDto.taskTemplate?.id)

        if (taskSchedulerDto.type == TaskSchedulerDto.TaskSchedulerTypeDto.DATE && (taskSchedulerDto.expireDelay == null || taskSchedulerDto.expireDelay!! < 1)) {
            throw ApplicationException(BusinessErrorInfo.TASK_SCHEDULER_EXPIRE_DELAY_INCORRECT)
        }

        if (taskSchedulerDto.type == TaskSchedulerDto.TaskSchedulerTypeDto.CRON && taskSchedulerDto.cron == null) {
            throw ApplicationException(BusinessErrorInfo.TASK_SCHEDULER_CRON_NOT_FOUND)
        }

        val taskScheduler = taskSchedulerRepository.save(taskSchedulerMapper.toEntity(taskSchedulerDto, TaskSchedulerEntity.TaskSchedulerStatus.ACTIVE, taskTemplate))
        quartzSchedulerService.scheduleJob(buildQuartzRequest(taskScheduler))

        return taskSchedulerMapper.toDto(taskScheduler)
    }

    @Transactional
    override fun update(id: String, cron: String): TaskSchedulerDto {
        val taskSchedulerEntity = taskSchedulerRepository.findByIdOrNull(id)
           ?: throw ApplicationException(BusinessErrorInfo.TASK_SCHEDULER_NOT_FOUND)

        taskSchedulerEntity.cron = cron
        val taskSchedulerEntityUpdated = taskSchedulerRepository.save(taskSchedulerEntity)
        quartzSchedulerService.updateJobTrigger(buildQuartzRequest(taskSchedulerEntityUpdated))

        return taskSchedulerMapper.toDto(taskSchedulerEntityUpdated)
    }

    @Transactional
    override fun delete(id: String) {
        val taskSchedulerEntity = taskSchedulerRepository.findByIdOrNull(id)
            ?: throw ApplicationException(BusinessErrorInfo.TASK_SCHEDULER_NOT_FOUND)

        taskSchedulerRepository.softDeletedTaskSchedulerById(id)
        quartzSchedulerService.deleteJob(buildQuartzRequest(taskSchedulerEntity))
    }

    private fun buildQuartzRequest(taskScheduler: TaskSchedulerEntity): QuartzRequest {
        return QuartzRequest(
            id = checkNotNull(taskScheduler.id),
            data = mapOf("id" to checkNotNull(taskScheduler.id)),
            jobName = QuartzRequest.JobName.TASK_CREATE,
            jobGroup = QuartzRequest.JobGroup.TASK_CREATE_GROUP,
            cronExpression = taskScheduler.cron,
            triggerDate = taskScheduler.triggerDate,
            jobClass = TaskQuartzJob::class.java,
            type = toJobType(checkNotNull(taskScheduler.type))
        )
    }
}