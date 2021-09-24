package com.example.achieve_goals.exceptions

import org.springframework.http.HttpStatus
import java.time.ZonedDateTime

data class ApiException(
    val message: String?,
    val httpStatus: HttpStatus,
    val timestamp: ZonedDateTime
)
