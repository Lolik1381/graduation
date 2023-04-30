package ru.stankin.graduation.config

import java.util.Properties
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource
import org.quartz.Scheduler
import org.springframework.boot.autoconfigure.quartz.QuartzProperties
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.scheduling.quartz.SchedulerFactoryBean
import org.springframework.transaction.PlatformTransactionManager
import ru.stankin.graduation.quartz.SchedulerJobFactory


@Configuration
class QuartzConfig {

    @Bean
    fun schedulerJobFactory(applicationContext: ApplicationContext): SchedulerJobFactory {
        val jobFactory = SchedulerJobFactory()
        jobFactory.setApplicationContext(applicationContext)

        return jobFactory
    }

    @Bean
    fun transactionManager(entityManagerFactory: EntityManagerFactory): PlatformTransactionManager {
        val transactionManager = JpaTransactionManager()
        transactionManager.entityManagerFactory = entityManagerFactory

        return transactionManager
    }

    @Bean
    fun schedulerFactoryBean(
        applicationContext: ApplicationContext,
        dataSource: DataSource,
        quartzProperties: QuartzProperties,
        transactionManager: PlatformTransactionManager
    ): SchedulerFactoryBean {
        val properties = Properties().apply { putAll(quartzProperties.properties) }

        val factory = SchedulerFactoryBean()
        factory.isAutoStartup = true
        factory.setOverwriteExistingJobs(true)
        factory.setDataSource(dataSource)
        factory.setTransactionManager(transactionManager)
        factory.setQuartzProperties(properties)
        factory.setJobFactory(schedulerJobFactory(applicationContext))
        factory.setWaitForJobsToCompleteOnShutdown(true)

        return factory
    }

    @Bean
    fun scheduler(factory: SchedulerFactoryBean): Scheduler = factory.scheduler
}