package ru.stankin.graduation.mapper

import org.mapstruct.AfterMapping
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingTarget
import org.mapstruct.Mappings
import ru.stankin.graduation.config.DefaultMapperConfig
import ru.stankin.graduation.dto.TaskCheckDto
import ru.stankin.graduation.entity.TaskCheckEntity

@Mapper(config = DefaultMapperConfig::class)
abstract class TaskCheckMapper {

    @Mappings(
        Mapping(target = "files", ignore = true),
        Mapping(source = "task.id", target = "taskId")
    )
    abstract fun toDto(source: TaskCheckEntity): TaskCheckDto

    @AfterMapping
    fun afterToDtoMapping(source: TaskCheckEntity, @MappingTarget target: TaskCheckDto): TaskCheckDto {
        return target.also { taskCheck ->
            taskCheck.files = source.files.map { it.id!! }
        }
    }
}