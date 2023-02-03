package com.example.achieve_goals.mapper

import com.example.achieve_goals.dto.SubTaskParentDTO
import com.example.achieve_goals.dto.TaskDTO
import com.example.achieve_goals.entities.Task
import org.springframework.stereotype.Component

@Component
class TaskMapperImpl : TaskMapper {
    override fun toDto(task: Task): TaskDTO {
        return TaskDTO(
            id = task.id,
            title = task.title,
            description = task.description,
            isDone = task.isDone,
            gid = task.gid,
            root= task.root,
            priority= task.priority,
            labels = task.labels.map { it.id },
            createdAt = task.createdAt,
            updatedAt = task.updatedAt,
            deadline = task.deadline
        )
    }

    override fun subTaskParentDtoFromGoal(task: Task): SubTaskParentDTO {
        return SubTaskParentDTO(
            parentId = task.id,
            parentTitle = task.title
        )
    }

}