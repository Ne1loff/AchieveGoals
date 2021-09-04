package com.example.achieve_goals.dto

import java.util.*

data class GoalDTO(
    val title: String?,

    val description: String?,

    val done: Boolean?,

    val deadline: Date
)
