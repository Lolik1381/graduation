package ru.stankin.graduation.entity

import java.time.ZonedDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table
import org.hibernate.annotations.GenericGenerator

@Entity
@Table(name = "task_scheduler")
class TaskSchedulerEntity(

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    var id: String? = null,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "task_template_id")
    var taskTemplate: TaskTemplateEntity? = null,

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    var type: TaskSchedulerType? = null,

    @Column(name = "cron")
    var cron: String? = null,

    @Column(name = "trigger_date")
    var triggerDate: ZonedDateTime? = null,

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    var status: TaskSchedulerStatus? = null,

    @Column(name = "user_id")
    var userId: String? = null,

    @Column(name = "group_id")
    var groupId: String? = null,

    @Column(name = "expire_delay", nullable = false)
    var expireDelay: Long? = null
) {

    enum class TaskSchedulerType {
        CRON, DATE
    }

    enum class TaskSchedulerStatus {
        ACTIVE, DELETED
    }
}