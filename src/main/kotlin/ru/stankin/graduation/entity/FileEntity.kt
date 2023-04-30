package ru.stankin.graduation.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "file")
class FileEntity(

    @Id
    @Column(name = "id", nullable = false, length = 36)
    var id: String? = null,

    @Column(name = "name")
    var name: String? = null,

    @Column(name = "extension")
    var extension: String? = null
)