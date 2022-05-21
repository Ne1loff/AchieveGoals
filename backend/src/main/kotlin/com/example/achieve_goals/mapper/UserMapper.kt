package com.example.achieve_goals.mapper

import com.example.achieve_goals.dto.RegistrationRequest
import com.example.achieve_goals.dto.UserDTO
import com.example.achieve_goals.entities.User


interface UserMapper {
    fun userFromRegistrationRequest(newUser: RegistrationRequest) : User
    fun dtoFromUser(user: User, locality: String, avatar: String?) : UserDTO
}