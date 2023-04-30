package ru.stankin.graduation.dto

data class TaskCheckDto(

    var id: String? = null,
    var taskId: String? = null,
    var taskTemplateCheckId: String? = null,
    var comment: String? = null,
    var controlValue: String? = null,
    var status: TaskCheckStatus? = null,
    var files: List<String>? = null
) {

    enum class TaskCheckStatus {
        ACTIVE, FINISHED
    }
}