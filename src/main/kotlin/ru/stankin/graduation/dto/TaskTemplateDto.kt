package ru.stankin.graduation.dto

data class TaskTemplateDto(
    var id: String? = null,
    var header: String? = null,
    var description: String? = null,
    var status: TaskTemplateDtoStatus? = null,
    var equipment: EquipmentDto? = null,
    var taskTemplateChecks: MutableList<TaskTemplateCheckDto>? = null
) {

    enum class TaskTemplateDtoStatus {
        DRAFT, ACTIVE, DELETED
    }
}