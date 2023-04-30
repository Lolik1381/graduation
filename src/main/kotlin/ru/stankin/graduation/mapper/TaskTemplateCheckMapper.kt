package ru.stankin.graduation.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingTarget
import org.mapstruct.Mappings
import ru.stankin.graduation.config.DefaultMapperConfig
import ru.stankin.graduation.dto.TaskTemplateCheckDto
import ru.stankin.graduation.entity.TaskTemplateCheckEntity
import ru.stankin.graduation.entity.TaskTemplateEntity

@Mapper(config = DefaultMapperConfig::class)
abstract class TaskTemplateCheckMapper {

    @Mappings(
        Mapping(source = "source.id", target = "id"),
        Mapping(source = "source.description", target = "description"),
        Mapping(source = "taskTemplate", target = "taskTemplate")
    )
    abstract fun toEntity(source: TaskTemplateCheckDto, taskTemplate: TaskTemplateEntity): TaskTemplateCheckEntity

    abstract fun toDto(source: TaskTemplateCheckEntity): TaskTemplateCheckDto

    @Mappings(
        Mapping(source = "source.id", target = "id"),
        Mapping(source = "source.description", target = "description"),
        Mapping(source = "taskTemplate", target = "taskTemplate")
    )
    abstract fun updateEntity(source: TaskTemplateCheckDto, taskTemplate: TaskTemplateEntity, @MappingTarget taskTemplateCheckEntity: TaskTemplateCheckEntity): TaskTemplateCheckEntity
}