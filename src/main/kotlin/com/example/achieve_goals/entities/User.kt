package com.example.achieve_goals.entities

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class User(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    val name: String,

    val surname: String,

    val email: String,

    val locality: String,

    val passwordHash: String,

    val userPhoto: Any //TODO (entities.UserPhoto)
)
