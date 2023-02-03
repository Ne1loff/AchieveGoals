package com.example.achieve_goals.dto

import java.util.*

data class TaskDTO(

    val id: Long,

    val title: String?,

    val description: String?,

    val isDone: Boolean?,

    val gid: Long?,

    val root: Long,

    val priority: Int?,

    val labels: List<Long>,

    val createdAt: Date?,

    val updatedAt: Date?,

    val deadline: Date?
)
