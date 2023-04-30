package ru.stankin.graduation.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import ru.stankin.graduation.config.DefaultMapperConfig
import ru.stankin.graduation.dto.TaskMetadataDto
import ru.stankin.graduation.entity.TaskEntity

@Mapper(config = DefaultMapperConfig::class)
abstract class TaskMetadataMapper {

    @Mappings(
        Mapping(source = "taskTemplate.header", target = "header"),
        Mapping(source = "taskTemplate.description", target = "description")
    )
    abstract fun toDto(taskEntity: TaskEntity): TaskMetadataDto
}