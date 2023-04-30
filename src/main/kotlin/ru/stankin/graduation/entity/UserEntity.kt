package ru.stankin.graduation.entity

import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.Table

@Entity
@Table(name = "system_user")
class UserEntity(

    @Id
    @Column(name = "id", nullable = false, length = 36)
    var id: String? = null,

    @Column(name = "username", nullable = false)
    var username: String? = null,

    @Column(name = "password", nullable = false)
    var password: String? = null,

    @Column(name = "first_name", nullable = false, length = 1000)
    var firstName: String? = null,

    @Column(name = "last_name", nullable = false, length = 1000)
    var lastName: String? = null,

    @Column(name = "email", nullable = false, length = 320)
    var email: String? = null,

    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(
        name = "user_role_link",
        joinColumns = [JoinColumn(name = "system_user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    var roles: MutableSet<RoleEntity>? = null,

    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(
        name = "system_user_group_link",
        joinColumns = [JoinColumn(name = "system_user_id")],
        inverseJoinColumns = [JoinColumn(name = "user_group_id")]
    )
    var groups: MutableSet<GroupEntity>? = null,
)