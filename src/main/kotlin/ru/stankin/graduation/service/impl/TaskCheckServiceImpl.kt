package ru.stankin.graduation.service.impl

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import ru.stankin.graduation.dto.TaskCheckDto
import ru.stankin.graduation.entity.TaskCheckEntity
import ru.stankin.graduation.exception.ApplicationException
import ru.stankin.graduation.exception.BusinessErrorInfo
import ru.stankin.graduation.mapper.TaskCheckMapper
import ru.stankin.graduation.repository.FileRepository
import ru.stankin.graduation.repository.TaskCheckRepository
import ru.stankin.graduation.repository.TaskTemplateCheckRepository
import ru.stankin.graduation.service.TaskCheckService
import ru.stankin.graduation.service.TaskService

@Service
class TaskCheckServiceImpl(
    private val taskCheckRepository: TaskCheckRepository,
    private val taskTemplateCheckRepository: TaskTemplateCheckRepository,
    private val fileRepository: FileRepository,
    private val taskCheckMapper: TaskCheckMapper,
    private val taskService: TaskService
) : TaskCheckService {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    override fun update(id: String, comment: String?, controlValue: String?, photoIds: List<String>?): TaskCheckDto {
        val taskCheck = taskCheckRepository.findByIdPessimisticAndStatus(id, TaskCheckEntity.TaskCheckStatus.ACTIVE)
            ?: throw ApplicationException(BusinessErrorInfo.TASK_CHECK_NOT_FOUND)

        val taskTemplateCheck = taskTemplateCheckRepository.findByIdOrNull(taskCheck.taskTemplateCheckId)
            ?: throw ApplicationException(BusinessErrorInfo.TASK_TEMPLATE_CHECK_NOT_FOUND)

        if (taskTemplateCheck.requiredComment == true && comment == null) {
            throw ApplicationException(BusinessErrorInfo.TASK_CHECK_COMMENT_NOT_FOUND)
        }

        if (taskTemplateCheck.requiredControlValue == true && controlValue == null) {
            throw ApplicationException(BusinessErrorInfo.TASK_CHECK_CONTROL_VALUE_NOT_FOUND)
        }

        if (taskTemplateCheck.requiredPhoto == true && photoIds.isNullOrEmpty()) {
            throw ApplicationException(BusinessErrorInfo.TASK_CHECK_FILES_NOT_FOUND)
        }

        taskCheck.comment = comment
        taskCheck.controlValue = controlValue
        taskCheck.status = TaskCheckEntity.TaskCheckStatus.FINISHED

        photoIds?.let { fileRepository.findAllById(it) }?.run { taskCheck.files = this }

        taskService.progress(taskCheck.task?.id!!)

        return taskCheckMapper.toDto(taskCheckRepository.save(taskCheck))
    }
}