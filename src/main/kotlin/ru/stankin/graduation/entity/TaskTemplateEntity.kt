package ru.stankin.graduation.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table
import org.hibernate.annotations.GenericGenerator

@Entity
@Table(name = "task_template")
class TaskTemplateEntity(

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    var id: String? = null,

    @Column(name = "header", nullable = false)
    var header: String? = null,

    @Column(name = "description", nullable = false)
    var description: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    var status: TaskTemplateStatus? = null,

    @OneToMany(mappedBy = "taskTemplate")
    var taskTemplateChecks: MutableSet<TaskTemplateCheckEntity>? = mutableSetOf()
) {

    enum class TaskTemplateStatus {
        DRAFT, ACTIVE, DELETED
    }
}