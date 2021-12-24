package com.example.achieve_goals.controller

import com.example.achieve_goals.dto.GoalDTO
import com.example.achieve_goals.dto.UserDTO
import com.example.achieve_goals.service.GoalService
import com.example.achieve_goals.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/api/admin/"])
class AdminController(
    val userService: UserService,
    val goalService: GoalService
) { // for debug

    @GetMapping("users")
    fun getUsers(): List<UserDTO> {
        return userService.getAllUsers()
    }
    @GetMapping("goals")
    fun getAllGoals(): MutableList<GoalDTO> {
        return goalService.getAllGoals()
    }
}