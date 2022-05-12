package com.example.achieve_goals.mapper

import com.example.achieve_goals.config.RegistrationRequest
import com.example.achieve_goals.dto.UserDTO
import com.example.achieve_goals.entities.User
import org.springframework.stereotype.Component

@Component
class UserMapperImpl : UserMapper {
    override fun userFromRegistrationRequest(newUser: RegistrationRequest): User {
        return User(
            id = -1,
            usernameSalt = newUser.username,
            male = newUser.male,
            email = newUser.email,
            locality = newUser.locality,
            passwordHash = newUser.password,
            admin = false,
            name = null,
            surname = null,
            userPhoto = null
        )
    }

    override fun dtoFromUser(user: User, locality: String, avatar: String?): UserDTO {
        return UserDTO(
            username = user.username,
            name = user.name,
            surname = user.surname,
            male = user.male,
            email = user.email,
            country = locality,
            avatar = avatar
        )
    }
}