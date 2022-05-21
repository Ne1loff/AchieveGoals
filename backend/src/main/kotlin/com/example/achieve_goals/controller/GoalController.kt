package com.example.achieve_goals.controller

import com.example.achieve_goals.dto.CreateGoalRequest
import com.example.achieve_goals.dto.GoalDTO
import com.example.achieve_goals.entities.User
import com.example.achieve_goals.exceptions.notFound.UserNotFoundException
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
        throw UserNotFoundException()
    }

    @GetMapping("{id}")
    fun getGoalById(@PathVariable id: Long, auth: Authentication): GoalDTO {
        val principal = auth.principal
        if (principal is User) {
            return goalService.getUserGoalById(principal.id, id)
        }
        throw UserNotFoundException()
    }


    @GetMapping("{id}/sub")
    fun getSubGoalsByParentId(@PathVariable id: Long, auth: Authentication): List<GoalDTO> {
        val principal = auth.principal
        if (principal is User) {
            return goalService.getSubGoalsByGid(principal.id, id)
        }
        throw UserNotFoundException()
    }

    @PostMapping
    fun createMainGoal(@RequestBody goalRequest: CreateGoalRequest, auth: Authentication): ResponseEntity<HttpStatus> {
        val principal = auth.principal
        if (principal is User) {
            goalService.createMainGoal(goalRequest, principal.id)
            return ResponseEntity.status(HttpStatus.CREATED).build()
        }
        throw UserNotFoundException()
    }

    @PutMapping
    fun updateGoal(@RequestBody goalsDTO: List<GoalDTO>, auth: Authentication): ResponseEntity<HttpStatus> {
        val principal = auth.principal
        if (principal is User) {
            goalService.updateGoal(goalsDTO, principal.id)
            return ResponseEntity.accepted().build()
        }
        throw UserNotFoundException()
    }

    @DeleteMapping
    fun deleteGoal(@RequestBody ids: List<Long>, auth: Authentication): ResponseEntity<HttpStatus> {
        val principal = auth.principal
        if (principal is User) {
            goalService.deleteGoal(ids, principal.id)
            return ResponseEntity.ok().build()
        }
        throw UserNotFoundException()
    }
}