package com.example.achieve_goals.utils

import com.example.achieve_goals.entities.User
import com.example.achieve_goals.exceptions.notFound.UserNotFoundException
import org.springframework.security.core.Authentication

fun Authentication.getUserEntity(): User = if (principal is User) principal as User else {
    throw UserNotFoundException()
}
