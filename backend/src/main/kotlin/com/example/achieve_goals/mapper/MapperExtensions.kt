package com.example.achieve_goals.mapper

import com.example.achieve_goals.dto.label.TaskLabelRequest
import com.example.achieve_goals.entities.TaskLabel

fun TaskLabel.toDto(): TaskLabelRequest = TaskLabelRequest(id, name)
fun TaskLabelRequest.toEntity(uid: Long): TaskLabel = TaskLabel(id ?: -1, uid, name)