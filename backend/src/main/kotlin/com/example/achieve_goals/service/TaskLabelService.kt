package com.example.achieve_goals.service

import com.example.achieve_goals.dto.TaskDTO
import com.example.achieve_goals.dto.label.TaskLabelRequest
import com.example.achieve_goals.entities.TaskLabel
import com.example.achieve_goals.exceptions.badRequest.IncorrectDataException
import com.example.achieve_goals.exceptions.notFound.LabelNotFoundException
import com.example.achieve_goals.mapper.TaskMapper
import com.example.achieve_goals.repository.TaskLabelRepository
import com.example.achieve_goals.repository.TaskRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class TaskLabelService(
    private val taskLabelRepository: TaskLabelRepository,
    private val taskRepository: TaskRepository,
    val mapper: TaskMapper
) {

    fun getUserLabels(userId: Long): List<TaskLabel> {
        return taskLabelRepository.getTaskLabelsByUid(userId).toMutableList()
    }

    @Transactional
    fun createLabel(taskLabelRequest: TaskLabelRequest, userId: Long) {
        val taskLabel = TaskLabel(-1L, userId, taskLabelRequest.name)
        taskLabelRepository.save(taskLabel)
    }

    @Transactional
    fun editLabel(taskLabelRequest: TaskLabelRequest, userId: Long) {
        val taskLabel = taskLabelRequest.id?.let {
            taskLabelRepository.getTaskLabelById(it)
        } ?: throw IncorrectDataException()

        if (taskLabel.uid != userId) throw LabelNotFoundException()

        taskLabel.name = taskLabelRequest.name
        taskLabelRepository.save(taskLabel)
    }

    fun getTasksWithLabel(tlId: Long, userId: Long): MutableList<TaskDTO> {
        if (!taskLabelRepository.existsTaskLabelByIdAndUid(tlId, userId)) throw LabelNotFoundException()
        return taskRepository.getTasksByUidAndLabelContains(userId, tlId).map(mapper::toDto).toMutableList()
    }

    @Transactional
    fun deleteLabel(tlId: Long, userId: Long) {
        if (!taskLabelRepository.existsTaskLabelByIdAndUid(tlId, userId)) throw LabelNotFoundException()

        taskLabelRepository.removeTasksLabelReference(tlId)
        taskLabelRepository.deleteTaskLabelById(tlId)
    }
}