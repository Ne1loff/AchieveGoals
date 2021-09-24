package com.example.achieve_goals.controller

import com.example.achieve_goals.dto.GoalDTO
import com.example.achieve_goals.entities.User
import com.example.achieve_goals.exceptions.ApiNotfoundException
import com.example.achieve_goals.service.GoalService
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/api/goals/"])
class GoalController(
    val goalService: GoalService
) {

    @GetMapping
    fun getAllUserGoals(auth: Authentication): MutableList<GoalDTO> {
        val principal = auth.principal
        if (principal is User) {
            return goalService.getUserGoals(principal.id).toMutableList()
        }
        throw ApiNotfoundException("User not found!")
    }

    @GetMapping("{gid}")
    fun getUserGoalById(@PathVariable gid: Long, auth: Authentication): GoalDTO {
        val principal = auth.principal
        if (principal is User) {
            return goalService.getUserGoalById(principal.id, gid)
        }
        throw ApiNotfoundException("User not found!")
    }

    @PostMapping
    fun createMainGoal(@RequestBody goalDTO: GoalDTO): HttpStatus {
        goalService.createMainGoal(goalDTO)
        return HttpStatus.CREATED
    }

    @PostMapping("sub-goals")
    fun createSubGoal(@RequestBody goalDTO: GoalDTO): HttpStatus {
        goalService.createSubGoal(goalDTO)
        return HttpStatus.CREATED
    }

    @PutMapping
    fun updateGoal(@RequestBody goalDTO: GoalDTO): HttpStatus {
        goalService.updateGoal(goalDTO)
        return HttpStatus.ACCEPTED
    }
}