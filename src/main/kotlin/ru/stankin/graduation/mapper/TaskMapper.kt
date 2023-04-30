package ru.stankin.graduation.mapper

import org.mapstruct.AfterMapping
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingTarget
import org.mapstruct.Mappings
import ru.stankin.graduation.config.DefaultMapperConfig
import ru.stankin.graduation.dto.TaskCheckDto
import ru.stankin.graduation.dto.TaskDto
import ru.stankin.graduation.entity.GroupEntity
import ru.stankin.graduation.entity.TaskEntity
import ru.stankin.graduation.entity.TaskTemplateEntity
import ru.stankin.graduation.entity.UserEntity

@Mapper(config = DefaultMapperConfig::class)
abstract class TaskMapper {

    @Mappings(
        Mapping(target = "id", ignore = true),
        Mapping(target = "createAt", ignore = true),
        Mapping(target = "updateAt", ignore = true),
        Mapping(target = "taskChecks", ignore = true),
        Mapping(source = "status", target = "status"),
        Mapping(source = "taskTemplateEntity", target = "taskTemplate"),
        Mapping(source = "userEntity", target = "systemUser"),
        Mapping(source = "groupEntity", target = "group"),
    )
    abstract fun toEntity(
        source: TaskDto,
        status: TaskDto.TaskStatusDto,
        taskTemplateEntity: TaskTemplateEntity,
        userEntity: UserEntity?,
        groupEntity: GroupEntity?
    ): TaskEntity

    @Mappings(
        Mapping(target = "taskChecks", ignore = true),
        Mapping(source = "systemUser.id", target = "systemUserId"),
        Mapping(source = "group.id", target = "groupId"),
        Mapping(source = "status", target = "taskStatus")
    )
    abstract fun toDto(source: TaskEntity): TaskDto

    @AfterMapping
    fun afterMappingToDto(source: TaskEntity, @MappingTarget target: TaskDto) {
        target.taskChecks = source.taskChecks.map {
            TaskCheckDto(
                id = it.id,
                taskId = it.task?.id,
                taskTemplateCheckId = it.taskTemplateCheckId,
                comment = it.comment,
                files = it.files.map { file -> file.id!! },
                status = TaskCheckDto.TaskCheckStatus.valueOf(it.status?.name!!),
                controlValue = it.controlValue
            )
        }
    }
}