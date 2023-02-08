package com.example.achieve_goals.controller

import com.example.achieve_goals.dto.TaskDTO
import com.example.achieve_goals.dto.label.TaskLabelRequest
import com.example.achieve_goals.entities.TaskLabel
import com.example.achieve_goals.service.TaskLabelService
import com.example.achieve_goals.utils.getUserEntity
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
    fun createLabel(auth: Authentication, @RequestBody labelDto: TaskLabelRequest): ResponseEntity<TaskLabelRequest> {
        val user = auth.getUserEntity()
        return ResponseEntity.ok(taskLabelService.createLabel(labelDto, user.id))
    }

    @PutMapping
    fun editLabel(auth: Authentication, @RequestBody labelDto: TaskLabelRequest): ResponseEntity<TaskLabelRequest> {
        val user = auth.getUserEntity()
        return ResponseEntity.ok(taskLabelService.editLabel(labelDto, user.id))
    }

    @DeleteMapping("{id}")
    fun deleteLabel(auth: Authentication, @PathVariable id: Long): ResponseEntity<Long> {
        val user = auth.getUserEntity()
        return ResponseEntity.ok(taskLabelService.deleteLabel(id, user.id))
    }
}