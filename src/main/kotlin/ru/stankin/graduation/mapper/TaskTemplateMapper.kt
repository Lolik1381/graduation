package ru.stankin.graduation.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingTarget
import org.mapstruct.Mappings
import ru.stankin.graduation.config.DefaultMapperConfig
import ru.stankin.graduation.dto.TaskTemplateDto
import ru.stankin.graduation.entity.TaskTemplateEntity

@Mapper(config = DefaultMapperConfig::class)
abstract class TaskTemplateMapper {

    @Mappings(
        Mapping(target = "taskTemplateChecks", ignore = true),
        Mapping(source = "status", target = "status")
    )
    abstract fun toEntity(source: TaskTemplateDto, status: TaskTemplateEntity.TaskTemplateStatus): TaskTemplateEntity

    abstract fun toDto(taskTemplate: TaskTemplateEntity): TaskTemplateDto

    @Mappings(
        Mapping(target = "taskTemplateChecks", ignore = true),
        Mapping(target = "status", ignore = true)
    )
    abstract fun updateEntity(source: TaskTemplateDto, @MappingTarget target: TaskTemplateEntity): TaskTemplateEntity
}