package ru.stankin.graduation.config

import org.mapstruct.MapperConfig
import org.mapstruct.ReportingPolicy

@MapperConfig(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
interface DefaultMapperConfig {
}