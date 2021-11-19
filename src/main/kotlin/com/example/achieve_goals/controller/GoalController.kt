package com.example.achieve_goals.controller

import com.example.achieve_goals.dto.GoalDTO
import com.example.achieve_goals.entities.User
import com.example.achieve_goals.exceptions.ApiNotfoundException
import com.example.achieve_goals.service.GoalService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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

    @GetMapping("{id}")
    fun getUserGoalById(@PathVariable id: Long, auth: Authentication): GoalDTO {
        val principal = auth.principal
        if (principal is User) {
            return goalService.getUserGoalById(principal.id, id)
        }
        throw ApiNotfoundException("User not found!")
    }


    @PostMapping
    fun createMainGoal(@RequestBody goalDTO: GoalDTO, auth: Authentication): ResponseEntity<HttpStatus> {
        val principal = auth.principal
        if (principal is User) {
            goalService.createMainGoal(goalDTO, principal.id)
            return ResponseEntity.status(HttpStatus.CREATED).build()
        }
        throw ApiNotfoundException("User not found!")
    }

    @PostMapping("sub-goals")
    fun createSubGoal(@RequestBody goalDTO: GoalDTO, auth: Authentication): ResponseEntity<HttpStatus> {
        val principal = auth.principal
        if (principal is User) {
            goalService.createSubGoal(goalDTO, principal.id)
            return ResponseEntity.status(HttpStatus.CREATED).build()
        }
        throw ApiNotfoundException("User not found!")
    }

    @PutMapping
    fun updateGoal(@RequestBody goalsDTO: List<GoalDTO>): ResponseEntity<HttpStatus> {
        goalService.updateGoal(goalsDTO)
        return ResponseEntity.accepted().build();
    }
}