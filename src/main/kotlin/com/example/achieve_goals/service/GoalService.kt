package com.example.achieve_goals.service

import com.example.achieve_goals.dto.GoalDTO
import com.example.achieve_goals.entities.Goal
import com.example.achieve_goals.exceptions.ApiBadRequestException
import com.example.achieve_goals.exceptions.ApiNotfoundException
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

    fun getAllGoals(): MutableList<GoalDTO> { // for admin in dev
        return goalRepository.findAll().map { goal -> mapper.dtoFromGoal(goal) }.toMutableList()
    }

    fun getUserGoals(userId: Long): MutableList<GoalDTO> {
        val goals = goalRepository.getGoalsByUid(userId)
            .map { goal -> mapper.dtoFromGoal(goal) }
            .toMutableList()
        goals.sortBy { it.gid }

        return goals
    }

    fun getUserGoalById(userId: Long, goalId: Long): GoalDTO {
        val goal = goalRepository.getGoalByIdOrderByCreatedAt(goalId) ?: throw ApiNotfoundException("Goal not found!")
        if (goal.uid == userId) {
            return mapper.dtoFromGoal(goal)
        }
        throw ApiBadRequestException("User doesn't have this goal")
    }

    fun createMainGoal(goalDTO: GoalDTO, uid: Long): Boolean {
        val date = Date()
        try {
            val goal = Goal(
                id = -1,
                uid = uid,
                title = goalDTO.title!!,
                description = goalDTO.description,
                isDone = false,
                gid = null,
                priority = goalDTO.priority!!,
                createdAt = date,
                updatedAt = date,
                deadline = goalDTO.deadline!!
            )
            goalRepository.save(goal)
        } catch (e: Exception) {
            throw ApiBadRequestException("Incorrect data")
        }
        return true
    }

    fun createSubGoal(goalDTO: GoalDTO, uid: Long): Boolean {
        if (goalDTO.gid == null) throw ApiBadRequestException("SubGoal must have parent")

        val date = Date()
        try {
            val goal = Goal(
                id = -1,
                uid = uid,
                title = goalDTO.title!!,
                description = goalDTO.description,
                isDone = false,
                gid = goalDTO.gid,
                priority = goalDTO.priority!!,
                createdAt = date,
                updatedAt = date,
                deadline = goalDTO.deadline!!
            )
            goalRepository.save(goal)
        } catch (e: Exception) {
            throw ApiBadRequestException("Incorrect data")
        }
        return true
    }

    @Transactional
    fun updateGoal(updGoals: List<GoalDTO>): Boolean {

        for (updGoal in updGoals) {
            val goal =
                goalRepository.getGoalByIdOrderByCreatedAt(updGoal.id) ?: throw ApiNotfoundException("Goal not found!")

            val date = Date()

            goal.title = updGoal.title ?: goal.title
            goal.description = updGoal.description ?: goal.description
            goal.isDone = updGoal.isDone ?: goal.isDone
            goal.updatedAt = date
            goal.deadline = updGoal.deadline ?: goal.deadline
        }
        return true
    }
}