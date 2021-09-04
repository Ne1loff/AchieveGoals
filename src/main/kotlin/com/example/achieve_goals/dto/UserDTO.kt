package com.example.achieve_goals.dto

data class UserDTO(
    val name: String,

    val surname: String,

    val email: String,

    val locality: String,

    val passwordHash: String
)
