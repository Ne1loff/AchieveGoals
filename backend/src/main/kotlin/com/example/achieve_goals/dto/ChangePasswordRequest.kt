package com.example.achieve_goals.dto

data class ChangePasswordRequest(
    val oldPassword: String,
    val newPassword: String
)
