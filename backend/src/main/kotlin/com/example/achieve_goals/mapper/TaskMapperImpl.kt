package com.example.achieve_goals.mapper

import com.example.achieve_goals.dto.SubTaskParentDTO
import com.example.achieve_goals.dto.TaskDTO
import com.example.achieve_goals.entities.Task
import org.springframework.stereotype.Component

@Component
class TaskMapperImpl : TaskMapper {
    override fun toDto(entity: Task): TaskDTO {
        return TaskDTO(
            id = entity.id,
            title = entity.title,
            description = entity.description,
            isDone = entity.isDone,
            gid = entity.gid,
            root = entity.root,
            priority = entity.priority,
            labels = entity.labels.map { it.id },
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
            deadline = entity.deadline
        )
    }

    override fun toEntity(dto: TaskDTO): Task {
        TODO("Not yet implemented")
    }

    override fun subTaskParentDtoFromGoal(task: Task): SubTaskParentDTO {
        return SubTaskParentDTO(
            parentId = task.id,
            parentTitle = task.title
        )
    }

}