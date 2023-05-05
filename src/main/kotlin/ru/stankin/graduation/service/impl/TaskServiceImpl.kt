package ru.stankin.graduation.service.impl

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.stankin.graduation.dto.TaskDto
import ru.stankin.graduation.dto.TaskMetadataDto
import ru.stankin.graduation.entity.TaskCheckEntity
import ru.stankin.graduation.entity.TaskEntity
import ru.stankin.graduation.entity.TaskTemplateEntity
import ru.stankin.graduation.exception.ApplicationException
import ru.stankin.graduation.exception.BusinessErrorInfo
import ru.stankin.graduation.mapper.TaskMapper
import ru.stankin.graduation.mapper.TaskMetadataMapper
import ru.stankin.graduation.repository.GroupRepository
import ru.stankin.graduation.repository.TaskRepository
import ru.stankin.graduation.repository.TaskTemplateRepository
import ru.stankin.graduation.repository.UserRepository
import ru.stankin.graduation.repository.util.getLikeText
import ru.stankin.graduation.service.TaskService
import ru.stankin.graduation.service.UserService

@Service
class TaskServiceImpl(
    private val userService: UserService,
    private val taskTemplateRepository: TaskTemplateRepository,
    private val taskRepository: TaskRepository,
    private val userRepository: UserRepository,
    private val groupRepository: GroupRepository,
    private val taskMapper: TaskMapper,
    private val taskMetadataMapper: TaskMetadataMapper
) : TaskService {

    @Transactional(readOnly = true)
    override fun findTaskById(taskId: String): TaskDto {
        return taskRepository.findByIdOrNull(taskId)?.run { taskMapper.toDto(this) }
            ?: throw ApplicationException(BusinessErrorInfo.TASK_NOT_FOUND)
    }

    @Transactional(readOnly = true)
    override fun findTasksMetadata(status: TaskDto.TaskStatusDto?, searchText: String?, equipmentId: String?): List<TaskMetadataDto> {
        val userDto = userService.getUserByContext()

        return taskRepository.findAllBySystemUserId(userDto.id!!, status?.name, searchText.getLikeText(), equipmentId)
            .map { taskMetadataMapper.toDto(it) }
    }

    @Transactional
    override fun create(taskDto: TaskDto): TaskDto {
        val taskTemplateEntity = taskTemplateRepository.findByIdAndStatus(taskDto.taskTemplate?.id, TaskTemplateEntity.TaskTemplateStatus.ACTIVE)
            ?: throw ApplicationException(BusinessErrorInfo.TASK_TEMPLATE_NOT_FOUND, taskDto.taskTemplate?.id)

        val userEntity = taskDto.systemUserId?.let {
            userRepository.findByIdOrNull(it)
                ?: throw ApplicationException(BusinessErrorInfo.USER_BY_ID_NOT_FOUND, it)
        }

        val groupEntity = taskDto.groupId?.let {
            groupRepository.findByIdOrNull(it)
                ?: throw ApplicationException(BusinessErrorInfo.GROUP_NOT_FOUND)
        }

        val taskEntity = taskMapper.toEntity(
            source = taskDto,
            status = TaskDto.TaskStatusDto.CREATED,
            taskTemplateEntity = taskTemplateEntity,
            userEntity = userEntity,
            groupEntity = groupEntity
        )

        taskTemplateEntity.taskTemplateChecks?.map {
            TaskCheckEntity(task = taskEntity, taskTemplateCheckId = it.id, status = TaskCheckEntity.TaskCheckStatus.ACTIVE)
        }?.run { taskEntity.taskChecks = this.toMutableList() }

        return taskMapper.toDto(taskRepository.save(taskEntity))
    }

    @Transactional
    override fun progress(id: String): TaskDto {
        val task = taskRepository.findByIdOrNull(id)
            ?: throw ApplicationException(BusinessErrorInfo.TASK_NOT_FOUND)

        if (task.status !in listOf(TaskEntity.TaskStatus.COMPLETE, TaskEntity.TaskStatus.EXPIRED)) {
            task.status = TaskEntity.TaskStatus.IN_PROGRESS
        }

        return taskMapper.toDto(taskRepository.save(task))
    }

    @Transactional
    override fun complete(id: String): TaskDto {
        val task = taskRepository.findByIdOrNull(id)
            ?: throw ApplicationException(BusinessErrorInfo.TASK_NOT_FOUND)

        if (task.status !in listOf(TaskEntity.TaskStatus.CREATED, TaskEntity.TaskStatus.EXPIRED)) {
            task.status = TaskEntity.TaskStatus.COMPLETE
        }

        return taskMapper.toDto(taskRepository.save(task))
    }
}