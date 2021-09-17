package com.example.achieve_goals.service

import com.example.achieve_goals.dto.GoalDTO
import com.example.achieve_goals.entities.Goal
import com.example.achieve_goals.mapper.GoalMapperImpl
import com.example.achieve_goals.repository.GoalRepository
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
class GoalService(
    val goalRepository: GoalRepository,
    val mapper: GoalMapperImpl
) {

    fun getAllGoals(): MutableList<GoalDTO> {
        return goalRepository.findAll().map { goal -> mapper.dtoFromGoal(goal) }.toMutableList()
    }

    fun getUserGoals(userId: Long): MutableList<GoalDTO> {
        val goals = goalRepository.getGoalsByUid(userId)
            .map { goal -> mapper.dtoFromGoal(goal) }
            .toMutableList()
        goals.sortBy { it.gid }

        return goals
    }

    fun createRootGoal(goalDTO: GoalDTO): Boolean {
        val date = Date()
        try {
            val goal = Goal(
                id = -1,
                uid = goalDTO.uid!!,
                title = goalDTO.title!!,
                description = goalDTO.description,
                isDone = false,
                gid = null,
                createdAt = date,
                updatedAt = date,
                deadline = goalDTO.deadline!!
            )
            goalRepository.save(goal)
        } catch (e: Exception) {
            return false
        }
        return true
    }

    fun createSubGoal(goalDTO: GoalDTO): Boolean {
        if (goalDTO.gid == null) return false

        val date = Date()
        try {
            val goal = Goal(
                id = -1,
                uid = goalDTO.uid!!,
                title = goalDTO.title!!,
                description = goalDTO.description,
                isDone = false,
                gid = goalDTO.gid,
                createdAt = date,
                updatedAt = date,
                deadline = goalDTO.deadline!!
            )
            goalRepository.save(goal)
        } catch (e: Exception) {
            return false
        }
        return true
    }

    @Transactional
    fun updateGoal(updGoal: GoalDTO): Boolean {

        val goal = goalRepository.getGoalById(updGoal.id) ?: return false
        if (goal.isDone) return false

        val date = Date()

        goal.title = updGoal.title ?: goal.title
        goal.description = updGoal.description ?: goal.description
        goal.isDone = if (checkForChildren(goal)) updGoal.isDone ?: goal.isDone else goal.isDone
        goal.updatedAt = date
        goal.deadline = updGoal.deadline ?: goal.deadline
        return true
    }

    private fun checkForChildren(goal: Goal): Boolean {
        return goalRepository.getGoalsByGidAndDone(goal.id, false).isEmpty()
    }
}