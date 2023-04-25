package com.example.achieve_goals.mapper

import com.example.achieve_goals.dto.RegistrationRequest
import com.example.achieve_goals.dto.UserDTO
import com.example.achieve_goals.dto.UserSettingsDto
import com.example.achieve_goals.entities.User
import com.example.achieve_goals.entities.UserSettings


interface UserMapper : Mapper<User, UserDTO> {
    fun userFromRegistrationRequest(newUser: RegistrationRequest): User
    fun dtoFromUser(user: User, locality: String, avatar: String?): UserDTO
    fun dtoFromUserSettings(userSettings: UserSettings): UserSettingsDto
}