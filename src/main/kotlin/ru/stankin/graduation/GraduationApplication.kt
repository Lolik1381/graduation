package ru.stankin.graduation

import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling
import ru.stankin.graduation.config.properties.SecurityProperty

@SpringBootApplication
@EnableConfigurationProperties(value = [SecurityProperty::class])
@EnableScheduling
@EnableSchedulerLock(defaultLockAtMostFor = "PT30S")
class GraduationApplication

fun main(args: Array<String>) {
    runApplication<GraduationApplication>(*args)
}
