package ru.stankin.graduation.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import ru.stankin.graduation.entity.TaskEntity

interface TaskRepository : JpaRepository<TaskEntity, String> {

    @Modifying
    @Query("""
        update task
            set status = 'EXPIRED'
        where expire_date < now()
            and status not in ('COMPLETE', 'EXPIRED')
    """, nativeQuery = true)
    fun expiredTask()

    @Query("""
        select t.id
            from task t
        where not exists(select * from task_check tc where tc.task_id = t.id and tc.status <> 'FINISHED')
    """, nativeQuery = true)
    fun findAllTaskWithAllCompleteChecks(): List<String>

    @Query("""
        select t.*
            from task t
        where t.system_user_id = :systemUserId
            or exists(select * from system_user_group_link sugl where sugl.system_user_id = :systemUserId and sugl.user_group_id = t.group_id)
    """, nativeQuery = true)
    fun findAllBySystemUserId(systemUserId: String): List<TaskEntity>
}