package ru.stankin.graduation.controller.user.v1

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import ru.stankin.graduation.dto.CommonResponse
import ru.stankin.graduation.dto.TaskCheckDto

@RequestMapping("/user/v1/taskCheck")
interface TaskCheckApi {

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: String,
        @RequestParam(required = false) comment: String?,
        @RequestParam(required = false) controlValue: String?,
        @RequestParam(required = false) photoIds: List<String>?
    ): CommonResponse<TaskCheckDto>
}