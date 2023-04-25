package com.example.achieve_goals.controller

import com.example.achieve_goals.dto.CreateTaskRequest
import com.example.achieve_goals.dto.TaskDTO
import com.example.achieve_goals.service.TaskService
import com.example.achieve_goals.utils.getUserEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/api/tasks/"])
class TaskController(
    val taskService: TaskService
) {

    @GetMapping
    fun getAllUserGoals(auth: Authentication): MutableList<TaskDTO> {
        val user = auth.getUserEntity()
        return taskService.getUserGoals(user.id).toMutableList()
    }

    @GetMapping("{id}")
    fun getGoalById(@PathVariable id: Long, auth: Authentication): TaskDTO {
        val user = auth.getUserEntity()
        return taskService.getUserGoalById(user.id, id)
    }


    @GetMapping("{id}/sub")
    fun getSubGoalsByParentId(@PathVariable id: Long, auth: Authentication): List<TaskDTO> {
        val user = auth.getUserEntity()
        return taskService.getSubGoalsByGid(user.id, id)
    }

    @PostMapping
    fun createMainGoal(@RequestBody goalRequest: CreateTaskRequest, auth: Authentication): ResponseEntity<TaskDTO> {
        val user = auth.getUserEntity()
        val task = taskService.createTask(goalRequest, user.id)

        return ResponseEntity(task, HttpStatus.CREATED)
    }

    @PutMapping
    fun updateGoal(@RequestBody goalsDTO: List<TaskDTO>, auth: Authentication): ResponseEntity<Collection<TaskDTO>> {
        val user = auth.getUserEntity()
        return ResponseEntity.ok(taskService.updateGoal(goalsDTO, user.id))
    }

    @DeleteMapping("{id}")
    fun deleteTaskById(@PathVariable id: Long, auth: Authentication): ResponseEntity<Long> {
        val user = auth.getUserEntity()
        return ResponseEntity.ok(taskService.deleteGoalById(id, user.id))
    }

    @DeleteMapping
    fun deleteGoal(@RequestBody ids: List<Long>, auth: Authentication): ResponseEntity<Collection<Long>> {
        val user = auth.getUserEntity()
        return ResponseEntity.ok(taskService.deleteGoals(ids, user.id))
    }
}