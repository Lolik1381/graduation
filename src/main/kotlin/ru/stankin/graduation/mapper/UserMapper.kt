package ru.stankin.graduation.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import ru.stankin.graduation.config.DefaultMapperConfig
import ru.stankin.graduation.dto.RequestUserDto
import ru.stankin.graduation.dto.ResponseUserDto
import ru.stankin.graduation.entity.RoleEntity
import ru.stankin.graduation.entity.UserEntity

@Mapper(config = DefaultMapperConfig::class)
abstract class UserMapper {

    @Mappings(
        Mapping(target = "groups", ignore = true),
        Mapping(source = "source.id", target = "id"),
        Mapping(source = "roles", target = "roles")
    )
    abstract fun toEntity(source: RequestUserDto, roles: List<RoleEntity>): UserEntity

    abstract fun toDto(source: UserEntity): ResponseUserDto
}