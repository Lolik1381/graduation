package ru.stankin.graduation.quartz.service

import org.quartz.Job
import org.quartz.JobDataMap
import org.quartz.JobDetail
import org.quartz.JobKey
import org.quartz.Scheduler
import org.quartz.SchedulerException
import org.quartz.TriggerKey
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationContext
import org.springframework.scheduling.quartz.JobDetailFactoryBean
import org.springframework.stereotype.Service
import ru.stankin.graduation.quartz.factory.TriggerFactory
import ru.stankin.graduation.quartz.model.QuartzRequest

@Service
class QuartzSchedulerServiceImpl(
    private val triggerFactory: TriggerFactory,
    private val scheduler: Scheduler,
    private val appContext: ApplicationContext
) : QuartzSchedulerService {

    companion object {
        private val logger = LoggerFactory.getLogger(QuartzSchedulerServiceImpl::class.java)
    }

    override fun scheduleJob(quartzRequest: QuartzRequest) {
        val jobKey = createJobKey(quartzRequest)

        val jobDetail = createJobDetail(quartzRequest.data, quartzRequest.jobClass, jobKey)
        val triggerKey = createJobTriggerKey(quartzRequest)
        val trigger = triggerFactory.createTrigger(quartzRequest, triggerKey)

        try {
            if (scheduler.checkExists(jobDetail.key)) {
                logger.warn("Job exists with jobKey: {}", jobKey.name)
                deleteAndUnscheduledJob(jobKey, triggerKey)
            }

            scheduler.scheduleJob(jobDetail, trigger)
            logger.debug("Job scheduled with jobKey: {}", jobKey.name)
        } catch (e: SchedulerException) {
            throw RuntimeException("Error scheduled job: jobName = ${jobKey.name}", e)
        }
    }

    override fun updateJobTrigger(quartzRequest: QuartzRequest) {
        val triggerKey = createJobTriggerKey(quartzRequest)
        val trigger = triggerFactory.createTrigger(quartzRequest, triggerKey)

        try {
            scheduler.rescheduleJob(triggerKey, trigger)
            logger.debug("Job rescheduled with jobTrigger: {}", triggerKey.name)
        } catch (e: SchedulerException) {
            throw RuntimeException("Error scheduled job: jobTrigger = ${triggerKey.name}", e)
        }
    }

    override fun deleteJob(quartzRequest: QuartzRequest) {
        val jobKey = createJobKey(quartzRequest)
        val triggerKey = createJobTriggerKey(quartzRequest)

        try {
            deleteAndUnscheduledJob(jobKey, triggerKey)
            logger.debug("Job deleted with jobName: {}", jobKey.name)
        } catch (e: SchedulerException) {
            throw RuntimeException("Error scheduled job: jobName = ${jobKey.name}", e)
        }
    }

    override fun startJobNow(quartzRequest: QuartzRequest) {
        val jobKey = createJobKey(quartzRequest)

        try {
            scheduler.triggerJob(jobKey)
            logger.debug("Job with jobName: {} scheduled and started now.", jobKey.name)
        } catch (e: SchedulerException) {
            throw RuntimeException("Failed to start job: jobName = ${jobKey.name}", e)
        }
    }

    private fun deleteAndUnscheduledJob(jobKey: JobKey, triggerKey: TriggerKey) {
        scheduler.deleteJob(jobKey)
        scheduler.unscheduleJob(triggerKey)
    }

    private fun createJobTriggerKey(quartzRequest: QuartzRequest): TriggerKey {
        val jobName = quartzRequest.jobName
        val jobGroup = quartzRequest.jobGroup

        return TriggerKey("${jobName.prefixJobName}.${jobName.prefixTriggerName}.${quartzRequest.id}", jobGroup.groupTriggerName)
    }

    private fun createJobKey(quartzRequest: QuartzRequest): JobKey {
        val jobName = quartzRequest.jobName
        val jobGroup = quartzRequest.jobGroup

        return JobKey("${jobName.prefixJobName}.${quartzRequest.id}", jobGroup.jobName)
    }

    private fun createJobDetail(data: Map<String, Any>, jobClass: Class<out Job>, jobKey: JobKey): JobDetail {
        val jobDataMap = JobDataMap().apply { putAll(data) }

        val factoryBean = JobDetailFactoryBean()
        factoryBean.setJobClass(jobClass)
        factoryBean.setDurability(false)
        factoryBean.setApplicationContext(appContext)
        factoryBean.setName(jobKey.name)
        factoryBean.setGroup(jobKey.group)
        factoryBean.jobDataMap = jobDataMap
        factoryBean.afterPropertiesSet()

        return factoryBean.getObject()!!
    }
}