package ru.stankin.graduation.extensions

import ru.stankin.graduation.dto.TaskCheckDto
import ru.stankin.graduation.exception.ApplicationException
import ru.stankin.graduation.exception.BusinessErrorInfo

fun TaskCheckDto.validate() {
    this.taskId ?: throw ApplicationException(BusinessErrorInfo.REQUIRED_ATTRIBUTE_NOT_SET, "taskId")
    this.taskTemplateCheckId ?: throw ApplicationException(BusinessErrorInfo.REQUIRED_ATTRIBUTE_NOT_SET, "taskCheckId")
}