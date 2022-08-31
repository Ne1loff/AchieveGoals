package com.example.achieve_goals.service

import com.example.achieve_goals.dto.CreateGoalRequest
import com.example.achieve_goals.dto.GoalDTO
import com.example.achieve_goals.dto.SubGoalParentDTO
import com.example.achieve_goals.entities.Goal
import com.example.achieve_goals.exceptions.notFound.GoalNotFoundException
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
        val goal = goalRepository.getGoalById(goalId) ?: throw GoalNotFoundException()
        if (goal.uid == userId) {
            return mapper.dtoFromGoal(goal)
        }
        throw GoalNotFoundException()
    }

    fun getSubGoalParents(userId: Long, goalId: Long): MutableList<SubGoalParentDTO> {
        val goal = goalRepository.getGoalById(goalId) ?: throw GoalNotFoundException()
        if (goal.uid == userId) {
            val goals = goalRepository.getGoalsByRoot(goal.root)
            val parents = ArrayList<Goal>()
            var currentGoal: Goal = goal
            while (currentGoal.gid != null) {
                currentGoal = goals.find { it.id == currentGoal.gid }!!
                parents.add(currentGoal)
            }
            return parents.map { mapper.subGoalParentDtoFromGoal(it) }.toMutableList()
        }
        throw GoalNotFoundException()
    }

    fun getSubGoalsByGid(userId: Long, gid: Long): MutableList<GoalDTO> {
        val goal = goalRepository.getGoalById(gid) ?: throw GoalNotFoundException()
        if (goal.uid == userId) {
            val goals = goalRepository.getGoalsByGid(gid)
            return goals.map { mapper.dtoFromGoal(it) }.toMutableList()
        }
        throw GoalNotFoundException()
    }

    fun createMainGoal(goalRequest: CreateGoalRequest, uid: Long) {
        val date = Date()

        val goal = Goal(
            id = -1,
            uid = uid,
            title = goalRequest.title,
            description = goalRequest.description,
            isDone = false,
            gid = goalRequest.gid,
            root = -1,
            priority = goalRequest.priority,
            createdAt = date,
            updatedAt = date,
            deadline = goalRequest.deadline
        )

        goal.root = goal.id // TODO: check for working

        goalRepository.save(goal)
    }


    @Transactional
    fun updateGoal(updGoals: List<GoalDTO>, userId: Long) {

        for (updGoal in updGoals) {
            val goal = goalRepository.getGoalById(updGoal.id) ?: throw GoalNotFoundException()

            if (goal.uid != userId) throw GoalNotFoundException()

            val date = Date()

            updGoal.title?.let { goal.title = it }
            updGoal.description?.let { goal.description = it }
            updGoal.isDone?.let { goal.isDone = it }
            updGoal.deadline?.let { goal.deadline = it }
            updGoal.priority?.let { goal.priority = it }
            goal.updatedAt = date
        }

    }

    @Transactional
    fun deleteGoal(ids: List<Long>, userId: Long) {

        for (id in ids) {
            if (goalRepository.existsGoalByIdAndUid(id, userId))
                goalRepository.deleteById(id)
            else
                throw GoalNotFoundException()
        }

    }

}