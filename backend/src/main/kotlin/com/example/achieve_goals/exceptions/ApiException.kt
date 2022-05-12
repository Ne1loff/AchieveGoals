package com.example.achieve_goals.exceptions

import java.time.ZonedDateTime

data class ApiException(
    val message: String?,
    val status: Int,
    val error: String,
    val timestamp: ZonedDateTime
)
