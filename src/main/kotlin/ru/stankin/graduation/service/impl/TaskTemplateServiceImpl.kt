package ru.stankin.graduation.service.impl

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.stankin.graduation.dto.TaskTemplateCheckDto
import ru.stankin.graduation.dto.TaskTemplateDto
import ru.stankin.graduation.entity.TaskTemplateCheckEntity
import ru.stankin.graduation.entity.TaskTemplateEntity
import ru.stankin.graduation.exception.ApplicationException
import ru.stankin.graduation.exception.BusinessErrorInfo
import ru.stankin.graduation.mapper.TaskTemplateCheckMapper
import ru.stankin.graduation.mapper.TaskTemplateMapper
import ru.stankin.graduation.repository.TaskTemplateCheckRepository
import ru.stankin.graduation.repository.TaskTemplateRepository
import ru.stankin.graduation.repository.util.getLikeText
import ru.stankin.graduation.service.TaskTemplateService

@Service
class TaskTemplateServiceImpl(
    private val taskTemplateRepository: TaskTemplateRepository,
    private val taskTemplateCheckRepository: TaskTemplateCheckRepository,
    private val taskTemplateMapper: TaskTemplateMapper,
    private val taskTemplateCheckMapper: TaskTemplateCheckMapper
): TaskTemplateService {

    @Transactional
    override fun create(taskTemplateDto: TaskTemplateDto): TaskTemplateDto {
        if (taskTemplateDto.taskTemplateChecks.isNullOrEmpty()) {
            throw ApplicationException(BusinessErrorInfo.TASK_TEMPLATE_CHECKS_EMPTY)
        }

        val taskTemplate = taskTemplateRepository.save(taskTemplateMapper.toEntity(taskTemplateDto, TaskTemplateEntity.TaskTemplateStatus.DRAFT))
        val taskTemplateChecks = taskTemplateDto.taskTemplateChecks?.map {
            it.controlValueType = it.controlValueType
            taskTemplateCheckMapper.toEntity(it, taskTemplate)
        }?.run { taskTemplateCheckRepository.saveAll(this) }

        taskTemplate.taskTemplateChecks = taskTemplateChecks?.toMutableSet()

        return taskTemplateMapper.toDto(taskTemplate)
    }

    @Transactional
    override fun updateTaskTemplate(id: String, taskTemplateDto: TaskTemplateDto): TaskTemplateDto {
        val taskTemplate = taskTemplateRepository.findByIdOrNull(id)
            ?: throw ApplicationException(BusinessErrorInfo.TASK_TEMPLATE_NOT_FOUND)

        val updatedTaskTemplate = taskTemplateRepository.save(taskTemplateMapper.updateEntity(taskTemplateDto, taskTemplate))

        val currentChecks = taskTemplateDto.taskTemplateChecks
            ?.filter { !it.id.isNullOrBlank() } ?: emptyList()

        val checkEntities = mutableListOf<TaskTemplateCheckEntity>().apply {
            val checks = currentChecks.map { check ->
                taskTemplateCheckMapper.updateEntity(check, updatedTaskTemplate, updatedTaskTemplate.taskTemplateChecks?.find { it.id == check.id }!!)
            }

            addAll(checks)
        }

        taskTemplateDto.taskTemplateChecks
            ?.filter { it.id.isNullOrBlank() }
            ?.map { taskTemplateCheckMapper.toEntity(it, updatedTaskTemplate) }
            ?.run { checkEntities.addAll(this) }

        taskTemplateCheckRepository.deleteAllByIdNotInAndTaskTemplateId(currentChecks.mapNotNull { it.id }, id)
        taskTemplateCheckRepository.saveAll(checkEntities)

        updatedTaskTemplate.taskTemplateChecks = taskTemplateCheckRepository.findAllByTaskTemplateId(id).toMutableSet()
        return taskTemplateMapper.toDto(updatedTaskTemplate)
    }

    override fun activeTaskTemplate(id: String): TaskTemplateDto {
        val taskTemplate = taskTemplateRepository.findByIdOrNull(id)
            ?: throw ApplicationException(BusinessErrorInfo.TASK_TEMPLATE_NOT_FOUND)

        if (taskTemplate.status != TaskTemplateEntity.TaskTemplateStatus.DRAFT) {
            throw ApplicationException(BusinessErrorInfo.TASK_TEMPLATE_ACTIVATE_IMPOSSIBLE)
        }

        taskTemplate.status = TaskTemplateEntity.TaskTemplateStatus.ACTIVE
        return taskTemplateMapper.toDto(taskTemplateRepository.save(taskTemplate))
    }

    override fun deleteTaskTemplate(id: String): TaskTemplateDto {
        val taskTemplate = taskTemplateRepository.findByIdOrNull(id)
            ?: throw ApplicationException(BusinessErrorInfo.TASK_TEMPLATE_NOT_FOUND)

        taskTemplate.status = TaskTemplateEntity.TaskTemplateStatus.DELETED
        return taskTemplateMapper.toDto(taskTemplateRepository.save(taskTemplate))
    }

    override fun findAll(searchText: String?, pageable: Pageable): Page<TaskTemplateDto> {
        return taskTemplateRepository.findAllByHeaderLike(searchText.getLikeText(), pageable)
            .map { taskTemplateMapper.toDto(it) }
    }

    override fun findById(id: String): TaskTemplateDto {
        return taskTemplateRepository.findByIdOrNull(id)?.run { taskTemplateMapper.toDto(this) }
            ?: throw ApplicationException(BusinessErrorInfo.TASK_TEMPLATE_NOT_FOUND, id)
    }
}