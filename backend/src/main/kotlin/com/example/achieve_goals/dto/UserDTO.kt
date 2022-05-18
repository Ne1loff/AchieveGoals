package com.example.achieve_goals.dto

data class UserDTO(

    val id: Long,

    val username: String?,

    val name: String?,

    val surname: String?,

    val male: Boolean?,

    val email: String?,

    val country: String?,

    val avatar: String?
)
