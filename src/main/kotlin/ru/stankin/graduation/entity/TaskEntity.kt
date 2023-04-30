package ru.stankin.graduation.entity

import java.time.ZoneId
import java.time.ZonedDateTime
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.PrePersist
import javax.persistence.PreUpdate
import javax.persistence.Table
import org.hibernate.annotations.GenericGenerator

@Entity
@Table(name = "task")
class TaskEntity(

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    var id: String? = null,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "task_template_id")
    var taskTemplate: TaskTemplateEntity? = null,

    @OneToMany(mappedBy = "task", cascade = [CascadeType.ALL])
    var taskChecks: MutableList<TaskCheckEntity> = mutableListOf(),

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    var status: TaskStatus? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "system_user_id")
    var systemUser: UserEntity? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    var group: GroupEntity? = null,

    @Column(name = "expire_date")
    var expireDate: ZonedDateTime? = null,

    @Column(name = "create_at")
    var createAt: ZonedDateTime? = null,

    @Column(name = "update_at")
    var updateAt: ZonedDateTime? = null
) {
    enum class TaskStatus {
        CREATED, IN_PROGRESS, COMPLETE, EXPIRED
    }

    @PrePersist
    fun initTimeStamps() {
        updateAt = ZonedDateTime.now(ZoneId.of("UTC"))
        createAt = createAt ?: updateAt
    }

    @PreUpdate
    fun updateTimeStamp() {
        updateAt = ZonedDateTime.now(ZoneId.of("UTC"))
    }
}