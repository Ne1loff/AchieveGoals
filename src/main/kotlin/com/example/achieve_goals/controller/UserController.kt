package com.example.achieve_goals.controller

import com.example.achieve_goals.config.RegistrationRequest
import com.example.achieve_goals.dto.UserDTO
import com.example.achieve_goals.entities.User
import com.example.achieve_goals.exceptions.ApiNotfoundException
import com.example.achieve_goals.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/api/"])
class UserController(
    val userService: UserService
) {

    @PostMapping("registration")
    fun registration(@RequestBody newUser: RegistrationRequest): HttpStatus {
        return if (userService.saveUser(newUser)) HttpStatus.CREATED else HttpStatus.CONFLICT
    }

    @GetMapping("user")
    fun getCurrentUser(auth: Authentication): UserDTO {
        val principal = auth.principal
        if (principal is User) {
            return userService.getUserById(principal.id)
        }
        throw ApiNotfoundException("User not found!")
    }
}