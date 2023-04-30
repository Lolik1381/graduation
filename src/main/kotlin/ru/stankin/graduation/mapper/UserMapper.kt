package ru.stankin.graduation.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import ru.stankin.graduation.config.DefaultMapperConfig
import ru.stankin.graduation.dto.UserDto
import ru.stankin.graduation.entity.RoleEntity
import ru.stankin.graduation.entity.UserEntity

@Mapper(config = DefaultMapperConfig::class)
abstract class UserMapper {

    @Mappings(
        Mapping(target = "groups", ignore = true),
        Mapping(source = "source.id", target = "id"),
        Mapping(source = "roles", target = "roles")
    )
    abstract fun toEntity(source: UserDto, roles: List<RoleEntity>): UserEntity

    @Mappings(
        Mapping(target = "password", ignore = true)
    )
    abstract fun toDto(source: UserEntity): UserDto
}