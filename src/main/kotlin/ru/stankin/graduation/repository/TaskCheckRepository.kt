package ru.stankin.graduation.repository

import javax.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import ru.stankin.graduation.entity.TaskCheckEntity

interface TaskCheckRepository: JpaRepository<TaskCheckEntity, String> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select tc from TaskCheckEntity tc where tc.id = :id and tc.status = :status")
    fun findByIdPessimisticAndStatus(id: String, status: TaskCheckEntity.TaskCheckStatus): TaskCheckEntity?
}