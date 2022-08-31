package com.example.achieve_goals.mapper

import com.example.achieve_goals.dto.GoalDTO
import com.example.achieve_goals.dto.SubGoalParentDTO
import com.example.achieve_goals.entities.Goal
import org.springframework.stereotype.Component

@Component
class GoalMapperImpl : GoalMapper {
    override fun dtoFromGoal(goal: Goal): GoalDTO {
        return GoalDTO(
            id = goal.id,
            title = goal.title,
            description = goal.description,
            isDone = goal.isDone,
            gid = goal.gid,
            root= goal.root,
            priority= goal.priority,
            createdAt = goal.createdAt,
            updatedAt = goal.updatedAt,
            deadline = goal.deadline
        )
    }

    override fun subGoalParentDtoFromGoal(goal: Goal): SubGoalParentDTO {
        return SubGoalParentDTO(
            parentId = goal.id,
            parentTitle = goal.title
        )
    }

}