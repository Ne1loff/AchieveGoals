package com.example.achieve_goals.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.*

data class GoalDTO(

    val id: Long,

    val uid: Long?,

    val title: String?,

    val description: String?,

    val isDone: Boolean?,

    val gid: Long?,

    val createdAt: Date?,

    val updatedAt: Date?,

    val deadline: Date?
)
