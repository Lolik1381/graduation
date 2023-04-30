package ru.stankin.graduation.scheduler

import net.javacrumbs.shedlock.core.SchedulerLock
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import ru.stankin.graduation.repository.TaskRepository
import ru.stankin.graduation.service.TaskService

@Component
class TaskSchedulerJobs(
    private val taskRepository: TaskRepository,
    private val taskService: TaskService
) {

    companion object {
        private val logger = LoggerFactory.getLogger(TaskSchedulerJobs::class.java)
    }

    @Transactional
    @Scheduled(fixedDelay = 5000)
    @SchedulerLock(name = "TaskScheduler.expiredTaskJob", lockAtLeastFor = 5000, lockAtMostFor = 5000)
    fun expiredTaskJob() {
        logger.debug("Expired task job started.")

        taskRepository.expiredTask()
    }

    @Scheduled(fixedDelay = 5000)
    @SchedulerLock(name = "TaskScheduler.completeTaskJob", lockAtLeastFor = 5000, lockAtMostFor = 5000)
    fun completeTaskJob() {
        logger.debug("Complete task job started.")

        taskRepository.findAllTaskWithAllCompleteChecks().forEach { taskService.complete(it) }
    }
}