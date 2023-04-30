package ru.stankin.graduation.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "graduation.security")
data class SecurityProperty(
    var jwtSecret: String? = null,
    var passwordLifetimeMs: Long? = null
)