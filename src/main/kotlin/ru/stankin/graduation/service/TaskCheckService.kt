package ru.stankin.graduation.service

import ru.stankin.graduation.dto.TaskCheckDto

interface TaskCheckService {

    fun update(id: String, comment: String?, controlValue: String?, photoIds: List<String>?): TaskCheckDto
}