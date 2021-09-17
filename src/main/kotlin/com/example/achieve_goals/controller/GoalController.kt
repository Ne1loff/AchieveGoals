package com.example.achieve_goals.controller

import com.example.achieve_goals.dto.GoalDTO
import com.example.achieve_goals.repository.GoalRepository
import com.example.achieve_goals.service.GoalService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/api/goals/"])
class GoalController(
    val goalService: GoalService
) {
    //TODO ()
}