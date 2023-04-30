package ru.stankin.graduation.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import ru.stankin.graduation.dto.TaskSchedulerDto
import ru.stankin.graduation.entity.TaskSchedulerEntity
import ru.stankin.graduation.entity.TaskTemplateEntity

@Mapper(componentModel = "spring")
abstract class TaskSchedulerMapper {

    abstract fun toDto(source: TaskSchedulerEntity): TaskSchedulerDto

    @Mappings(
        Mapping(source = "source.id", target = "id"),
        Mapping(source = "status", target = "status"),
        Mapping(source = "taskTemplateEntity", target = "taskTemplate")
    )
    abstract fun toEntity(source: TaskSchedulerDto, status: TaskSchedulerEntity.TaskSchedulerStatus, taskTemplateEntity: TaskTemplateEntity): TaskSchedulerEntity
}