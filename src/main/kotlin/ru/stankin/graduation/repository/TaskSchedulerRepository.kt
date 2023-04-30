package ru.stankin.graduation.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import ru.stankin.graduation.entity.TaskSchedulerEntity

interface TaskSchedulerRepository : JpaRepository<TaskSchedulerEntity, String> {

    @Modifying
    @Query("""
        update task_scheduler
            set status = 'DELETED'
        where id = :id
    """, nativeQuery = true)
    fun softDeletedTaskSchedulerById(id: String)
}