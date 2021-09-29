package com.example.achieve_goals.entities

import javax.persistence.*

@Entity
@Table(name = "users_avatar")
data class UserAvatar(
    @Id
    @Column(name = "id")
    val id: Long,

    @Column(name = "file_extension")
    var fileExtension: String
)
