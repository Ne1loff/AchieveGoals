package com.example.achieve_goals.repository

import com.example.achieve_goals.entities.TaskLabel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface TaskLabelRepository : JpaRepository<TaskLabel, Long> {
    fun getTaskLabelsByUid(uid: Long): Collection<TaskLabel>
    fun getTaskLabelById(id: Long): TaskLabel

    @Query(value = "SELECT * from task_label_table WHERE id in ?1", nativeQuery = true)
    fun getTaskLabelsByIds(ids: Collection<Long>): Collection<TaskLabel>
    fun existsTaskLabelByIdAndUid(id: Long, uid: Long): Boolean
    fun deleteTaskLabelById(id: Long)
    @Modifying
    @Query(value = "DELETE FROM task_table_labels WHERE labels_id = ?1", nativeQuery = true)
    fun removeTasksLabelReference(id: Long)
}