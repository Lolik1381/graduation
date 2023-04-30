package ru.stankin.graduation.config

import javax.sql.DataSource
import net.javacrumbs.shedlock.core.LockProvider
import net.javacrumbs.shedlock.provider.jdbctemplate.JdbcTemplateLockProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ShedlockConfig {

    @Bean
    fun lockProvider(datasource: DataSource): LockProvider = JdbcTemplateLockProvider(datasource)
}