package com.example.achieve_goals.service

import com.example.achieve_goals.dto.CreateTaskRequest
import com.example.achieve_goals.dto.SubTaskParentDTO
import com.example.achieve_goals.dto.TaskDTO
import com.example.achieve_goals.entities.Task
import com.example.achieve_goals.exceptions.notFound.GoalNotFoundException
import com.example.achieve_goals.mapper.TaskMapper
import com.example.achieve_goals.repository.TaskLabelRepository
import com.example.achieve_goals.repository.TaskRepository
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
class TaskService(
    val taskRepository: TaskRepository,
    val taskLabelRepository: TaskLabelRepository,
    val mapper: TaskMapper
) {

    fun getAllGoals(): MutableList<TaskDTO> { // for admin in dev
        return taskRepository.findAll().map { goal -> mapper.toDto(goal) }.toMutableList()
    }

    fun getUserGoals(userId: Long): MutableList<TaskDTO> {
        return taskRepository.getTasksByUidOrderByCreatedAt(userId)
            .map { goal -> mapper.toDto(goal) }
            .toMutableList()
    }

    fun getUserGoalById(userId: Long, goalId: Long): TaskDTO {
        val goal = taskRepository.getTaskById(goalId) ?: throw GoalNotFoundException()
        if (goal.uid == userId) {
            return mapper.toDto(goal)
        }
        throw GoalNotFoundException()
    }

    fun getSubGoalParents(userId: Long, goalId: Long): MutableList<SubTaskParentDTO> {
        val goal = taskRepository.getTaskById(goalId) ?: throw GoalNotFoundException()
        if (goal.uid == userId) {
            val goals = taskRepository.getTasksByRoot(goal.root)
            val parents = ArrayList<Task>()
            var currentTask: Task = goal
            while (currentTask.gid != null) {
                currentTask = goals.find { it.id == currentTask.gid }!!
                parents.add(currentTask)
            }
            return parents.map { mapper.subTaskParentDtoFromGoal(it) }.toMutableList()
        }
        throw GoalNotFoundException()
    }

    fun getSubGoalsByGid(userId: Long, gid: Long): MutableList<TaskDTO> {
        val goal = taskRepository.getTaskById(gid) ?: throw GoalNotFoundException()
        if (goal.uid == userId) {
            val goals = taskRepository.getTasksByGid(gid)
            return goals.map { mapper.toDto(it) }.toMutableList()
        }
        throw GoalNotFoundException()
    }

    fun createTask(goalRequest: CreateTaskRequest, uid: Long): TaskDTO {
        val date = Date()

        val isRoot = goalRequest.gid == null
        val labels = taskLabelRepository.getTaskLabelsByIds(goalRequest.labels)

        var task = Task(
            id = -1,
            uid = uid,
            title = goalRequest.title,
            description = goalRequest.description,
            isDone = false,
            gid = goalRequest.gid,
            root = goalRequest.gid ?: -1,
            priority = goalRequest.priority,
            labels = labels.toMutableList(),
            createdAt = date,
            updatedAt = date,
            deadline = goalRequest.deadline
        )

        task = taskRepository.save(task)

        if (isRoot) {
            task.root = task.id
            task = taskRepository.save(task)
        }

        return mapper.toDto(task)
    }


    @Transactional
    fun updateGoal(updGoals: List<TaskDTO>, userId: Long): MutableList<TaskDTO> {

        val tasks = mutableListOf<Task>()

        val ids = updGoals.map { it.id }
        val goals = taskRepository.getTasksByIds(ids).associateBy { it.id }

        for (updGoal in updGoals) {
            val goal = goals[updGoal.id] ?: throw GoalNotFoundException()
            if (goal.uid != userId) throw GoalNotFoundException()

            val date = Date()

            updGoal.title?.let { goal.title = it }
            updGoal.description?.let { goal.description = it }
            updGoal.isDone?.let { goal.isDone = it }
            updGoal.deadline?.let { goal.deadline = it }
            updGoal.priority?.let { goal.priority = it }

            if (updGoal.labels.isNotEmpty()) {
                val labels = taskLabelRepository.getTaskLabelsByIds(updGoal.labels)
                goal.labels = labels.toMutableList()
            } else {
                goal.labels = mutableListOf()
            }

            goal.updatedAt = date

            tasks.add(goal)
        }

        taskRepository.saveAll(tasks)
        return tasks.map(mapper::toDto).toMutableList()
    }

    @Transactional
    fun deleteGoalById(id: Long, userId: Long): Long {
        if (taskRepository.existsTaskByIdAndUid(id, userId))
            taskRepository.deleteById(id)
        else
            throw GoalNotFoundException()

        return id
    }

    @Transactional
    fun deleteGoals(ids: List<Long>, userId: Long): List<Long> {

        for (id in ids) {
            if (taskRepository.existsTaskByIdAndUid(id, userId))
                taskRepository.deleteById(id)
            else
                throw GoalNotFoundException()
        }

        return ids;
    }

}