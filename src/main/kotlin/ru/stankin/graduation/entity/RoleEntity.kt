package ru.stankin.graduation.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "role")
class RoleEntity(

    @Id
    @Column(name = "id", nullable = false, length = 36)
    var id: String? = null,

    @Column(name = "name", nullable = false)
    var name: String? = null
)