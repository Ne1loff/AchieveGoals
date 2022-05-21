package com.example.achieve_goals.dto

import java.util.*

data class CreateGoalRequest(
    val id: Long?,

    val title: String,

    val description: String?,

    val isDone: Boolean?,

    val gid: Long?,

    val priority: Int,

    val createdAt: Date?,

    val updatedAt: Date?,

    val deadline: Date
)
