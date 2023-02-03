package com.example.achieve_goals.controller

import com.example.achieve_goals.dto.TaskDTO
import com.example.achieve_goals.dto.label.TaskLabelRequest
import com.example.achieve_goals.entities.TaskLabel
import com.example.achieve_goals.service.TaskLabelService
import com.example.achieve_goals.utils.getUserEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/api/labels/"])
class TaskLabelController(
    val taskLabelService: TaskLabelService
) {

    @GetMapping
    fun getAllUserLabels(auth: Authentication): MutableList<TaskLabel> {
        val user = auth.getUserEntity()
        return taskLabelService.getUserLabels(user.id).toMutableList()
    }

    @GetMapping("{id}/tasks")
    fun getTaskWithLabel(auth: Authentication, @PathVariable id: Long): MutableList<TaskDTO> {
        val user = auth.getUserEntity()
        return taskLabelService.getTasksWithLabel(id, user.id)
    }

    @PostMapping
    fun createLabel(auth: Authentication, @RequestBody labelDto: TaskLabelRequest): ResponseEntity<HttpStatus> {
        val user = auth.getUserEntity()
        taskLabelService.createLabel(labelDto, user.id)

        return ResponseEntity.ok().build()
    }

    @PutMapping
    fun editLabel(auth: Authentication, @RequestBody labelDto: TaskLabelRequest): ResponseEntity<HttpStatus> {
        val user = auth.getUserEntity()
        taskLabelService.editLabel(labelDto, user.id)

        return ResponseEntity.ok().build()
    }

    @DeleteMapping("{id}")
    fun deleteLabel(auth: Authentication, @PathVariable id: Long): ResponseEntity<HttpStatus> {
        val user = auth.getUserEntity()
        taskLabelService.deleteLabel(id, user.id)

        return ResponseEntity.ok().build()
    }
}