package com.example.achieve_goals.mapper

import com.example.achieve_goals.dto.SubTaskParentDTO
import com.example.achieve_goals.dto.TaskDTO
import com.example.achieve_goals.entities.Task

interface TaskMapper : Mapper<Task, TaskDTO> {
    fun subTaskParentDtoFromGoal(task: Task): SubTaskParentDTO
}