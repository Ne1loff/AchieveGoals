package com.example.achieve_goals.dto

data class ChangePasswordRequestDTO(
    val oldPassword: String,
    val newPassword: String
)
