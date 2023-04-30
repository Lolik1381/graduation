package ru.stankin.graduation.quartz.service

import ru.stankin.graduation.quartz.model.QuartzRequest

interface QuartzSchedulerService {

    /**
     * Создать Cron Job
     */
    fun scheduleJob(quartzRequest: QuartzRequest)

    /**
     * Обновить Cron Job
     */
    fun updateJobTrigger(quartzRequest: QuartzRequest)

    /**
     * Удалить Job
     */
    fun deleteJob(quartzRequest: QuartzRequest)

    /**
     * Запустить Job
     */
    fun startJobNow(quartzRequest: QuartzRequest)
}