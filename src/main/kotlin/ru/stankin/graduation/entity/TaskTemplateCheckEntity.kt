package ru.stankin.graduation.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table
import org.hibernate.annotations.GenericGenerator

@Entity
@Table(name = "task_template_check")
class TaskTemplateCheckEntity(

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    var id: String? = null,

    @ManyToOne
    @JoinColumn(name = "task_template_id")
    var taskTemplate: TaskTemplateEntity? = null,

    @Column(name = "name")
    var name: String? = null,

    @Column(name = "description")
    var description: String? = null,

    @Column(name = "required_photo")
    var requiredPhoto: Boolean? = null,

    @Column(name = "required_comment")
    var requiredComment: Boolean? = null,

    @Column(name = "required_control_value")
    var requiredControlValue: Boolean? = null,

    @Column(name = "check_order")
    var taskTemplateCheckOrder: Int? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "control_value_type")
    var controlValueType: ControlValueType? = null
) {

    enum class ControlValueType {
        INTEGER, STRING
    }
}