package com.example.achieve_goals.mapper

import com.example.achieve_goals.dto.GoalDTO
import com.example.achieve_goals.dto.SubGoalParentDTO
import com.example.achieve_goals.entities.Goal

interface GoalMapper {
    fun dtoFromGoal(goal: Goal) : GoalDTO
    fun subGoalParentDtoFromGoal(goal: Goal) : SubGoalParentDTO
}