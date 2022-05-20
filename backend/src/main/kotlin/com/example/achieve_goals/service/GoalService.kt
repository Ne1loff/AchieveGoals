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
        return goalRepository.getGoalsByUidOrderByCreatedAt(userId)
            .map { goal -> mapper.dtoFromGoal(goal) }
            .toMutableList()
    }

    fun getUserGoalById(userId: Long, goalId: Long): GoalDTO {
        val goal = goalRepository.getGoalById(goalId) ?: throw ApiNotfoundException("Goal not found!")
        if (goal.uid == userId) {
            return mapper.dtoFromGoal(goal)
        }
        throw ApiNotfoundException("Goal not found!")
    }


    fun getSubGoalsByGid(userId: Long, gid: Long): MutableList<GoalDTO> {
        val goal = goalRepository.getGoalById(gid) ?: throw ApiNotfoundException("Goal not found!")
        if (goal.uid == userId) {
            val goals = goalRepository.getGoalsByGid(gid)
            return goals.map { mapper.dtoFromGoal(it) }.toMutableList()
        }
        throw ApiNotfoundException("Goal not found!")
    }

    fun createMainGoal(goalDTO: GoalDTO, uid: Long) {
        val date = Date()

        val goal = Goal(
            id = -1,
            uid = uid,
            title = goalDTO.title?.run { goalDTO.title } ?: throw ApiBadRequestException("Incorrect data"),
            description = goalDTO.description,
            isDone = false,
            gid = null,
            priority = goalDTO.priority?.run { goalDTO.priority } ?: throw ApiBadRequestException("Incorrect data"),
            createdAt = date,
            updatedAt = date,
            deadline = goalDTO.deadline?.run { goalDTO.deadline } ?: throw ApiBadRequestException("Incorrect data"),
        )

        goalRepository.save(goal)
    }

    fun createSubGoal(goalDTO: GoalDTO, uid: Long) {
        if (goalDTO.gid == null) throw ApiBadRequestException("SubGoal must have parent")

        val date = Date()

        val goal = Goal(
            id = -1,
            uid = uid,
            title = goalDTO.title?.run { goalDTO.title } ?: throw ApiBadRequestException("Incorrect data"),
            description = goalDTO.description,
            isDone = false,
            gid = goalDTO.gid,
            priority = goalDTO.priority?.run { goalDTO.priority } ?: throw ApiBadRequestException("Incorrect data"),
            createdAt = date,
            updatedAt = date,
            deadline = goalDTO.deadline?.run { goalDTO.deadline } ?: throw ApiBadRequestException("Incorrect data")
        )
        goalRepository.save(goal)
    }

    @Transactional
    fun updateGoal(updGoals: List<GoalDTO>, userId: Long) {

        for (updGoal in updGoals) {
            val goal =
                goalRepository.getGoalById(updGoal.id) ?: throw ApiNotfoundException("Goal not found!")

            if (goal.uid != userId)
                throw ApiBadRequestException("User doesn't have this goal(goal_id=${goal.id})")

            val date = Date()

            goal.title = updGoal.title ?: goal.title
            goal.description = updGoal.description ?: goal.description
            goal.isDone = updGoal.isDone ?: goal.isDone
            goal.updatedAt = date
            goal.deadline = updGoal.deadline ?: goal.deadline
            goal.priority = updGoal.priority ?: goal.priority
        }

    }

    @Transactional
    fun deleteGoal(ids: List<Long>, userId: Long) {

        for (id in ids) {
            if (goalRepository.existsGoalByIdAndUid(id, userId))
                goalRepository.deleteById(id)
            else
                throw ApiBadRequestException("The task doesn't exist or doesn't belong to a user")
        }

    }

}