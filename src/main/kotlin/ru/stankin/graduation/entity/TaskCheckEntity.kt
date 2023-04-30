package ru.stankin.graduation.entity

import java.time.ZoneId
import java.time.ZonedDateTime
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.PrePersist
import javax.persistence.PreUpdate
import javax.persistence.Table
import org.hibernate.annotations.GenericGenerator

@Entity
@Table(name = "task_check")
class TaskCheckEntity(

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    var id: String? = null,

    @ManyToOne
    @JoinColumn(name = "task_id")
    var task: TaskEntity? = null,

    @JoinColumn(name = "task_check_id")
    var taskTemplateCheckId: String? = null,

    @Column(name = "comment")
    var comment: String? = null,

    @Column(name = "control_value")
    var controlValue: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    var status: TaskCheckStatus? = null,

    @OneToMany(cascade = [CascadeType.ALL])
    @JoinTable(
        name = "task_check_file_link",
        joinColumns = [JoinColumn(name = "task_check_id")],
        inverseJoinColumns = [JoinColumn(name = "file_id")]
    )
    var files: MutableList<FileEntity> = mutableListOf(),

    @Column(name = "create_at")
    var createAt: ZonedDateTime? = null,

    @Column(name = "update_at")
    var updateAt: ZonedDateTime? = null
) {

    enum class TaskCheckStatus {
        ACTIVE, FINISHED
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