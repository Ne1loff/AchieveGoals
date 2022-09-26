package com.example.achieve_goals.mapper

import com.example.achieve_goals.dto.GoalDTO
import com.example.achieve_goals.dto.SubGoalParentDTO
import com.example.achieve_goals.entities.Goal

interface GoalMapper {
    fun toDto(goal: Goal) : GoalDTO
    fun subGoalParentDtoFromGoal(goal: Goal) : SubGoalParentDTO
}