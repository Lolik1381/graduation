package ru.stankin.graduation.dto

data class TaskTemplateCheckDto(
    var id: String? = null,
    var name: String? = null,
    var description: String? = null,
    var requiredPhoto: Boolean = false,
    var requiredComment: Boolean = false,
    var requiredControlValue: Boolean = false,
    var taskTemplateCheckOrder: Int? = null,
    var controlValueType: ControlValueType? = null
) {

    enum class ControlValueType {
        INTEGER, STRING
    }
}