package ru.stankin.graduation.mapper

import org.mapstruct.Mapper
import ru.stankin.graduation.dto.FileDto
import ru.stankin.graduation.entity.FileEntity

@Mapper(componentModel = "spring")
abstract class FileMapper {

    abstract fun toDto(source: FileEntity): FileDto
}