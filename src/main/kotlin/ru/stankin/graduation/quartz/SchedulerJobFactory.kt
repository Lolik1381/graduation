package ru.stankin.graduation.quartz

import org.quartz.spi.TriggerFiredBundle
import org.springframework.beans.factory.config.AutowireCapableBeanFactory
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.scheduling.quartz.SpringBeanJobFactory

class SchedulerJobFactory : SpringBeanJobFactory(), ApplicationContextAware {

    private lateinit var appContext: ApplicationContext
    private lateinit var beanFactory: AutowireCapableBeanFactory

    override fun setApplicationContext(context: ApplicationContext) {
        appContext = context
        beanFactory = context.autowireCapableBeanFactory
    }

    override fun createJobInstance(bundle: TriggerFiredBundle): Any {
        val job = super.createJobInstance(bundle)
        beanFactory.autowireBean(job)
        return job
    }
}