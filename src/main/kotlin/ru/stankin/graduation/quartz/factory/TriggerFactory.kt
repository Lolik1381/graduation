package ru.stankin.graduation.quartz.factory

import java.text.ParseException
import java.time.ZonedDateTime
import java.util.Date
import org.quartz.CronTrigger
import org.quartz.SimpleTrigger
import org.quartz.Trigger
import org.quartz.TriggerKey
import org.springframework.scheduling.quartz.CronTriggerFactoryBean
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean
import org.springframework.stereotype.Component
import ru.stankin.graduation.exception.ApplicationException
import ru.stankin.graduation.exception.BusinessErrorInfo
import ru.stankin.graduation.quartz.model.QuartzRequest

@Component
class TriggerFactory {

    fun createTrigger(quartzRequest: QuartzRequest, triggerKey: TriggerKey): Trigger {
        return when (quartzRequest.type) {
            QuartzRequest.JobType.CRON -> createCronTrigger(triggerKey, checkNotNull(quartzRequest.cronExpression))
            QuartzRequest.JobType.SIMPLE_TRIGGER -> createSimpleTrigger(triggerKey, checkNotNull(quartzRequest.triggerDate))
        }
    }

    private fun createCronTrigger(triggerKey: TriggerKey, cronExpression: String): CronTrigger {
        val factoryBean = CronTriggerFactoryBean()
        factoryBean.setName(triggerKey.name)
        factoryBean.setGroup(triggerKey.group)
        factoryBean.setStartTime(Date())
        factoryBean.setCronExpression(cronExpression)
        factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW)

        try {
            factoryBean.afterPropertiesSet()
        } catch (e: ParseException) {
            throw RuntimeException("Error created cron trigger: triggerKey = ${triggerKey.name}", e)
        }
        return factoryBean.getObject()!!
    }

    private fun createSimpleTrigger(triggerKey: TriggerKey, triggerDate: ZonedDateTime): SimpleTrigger {
        if (triggerDate.isBefore(ZonedDateTime.now())) {
            throw ApplicationException(BusinessErrorInfo.TASK_SCHEDULER_TRIGGER_DATE_IS_BEFORE_NOW)
        }

        val factoryBean = SimpleTriggerFactoryBean()
        factoryBean.setName(triggerKey.name)
        factoryBean.setGroup(triggerKey.group)
        factoryBean.setStartTime(Date.from(triggerDate.toInstant()))
        factoryBean.setRepeatInterval(1000)
        factoryBean.setRepeatCount(0)
        factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW)

        try {
            factoryBean.afterPropertiesSet()
        } catch (e: ParseException) {
            throw RuntimeException("Error created simple trigger: triggerKey = ${triggerKey.name}", e)
        }
        return factoryBean.getObject()!!
    }
}