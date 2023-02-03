package com.example.achieve_goals.repository

import com.example.achieve_goals.entities.Task
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface TaskRepository : JpaRepository<Task, Long> {

    fun getTasksByGid(gid: Long): MutableList<Task>

    fun getTasksByUidOrderByCreatedAt(uid: Long): MutableList<Task>

    fun getTasksByRoot(root: Long): MutableList<Task>

    fun existsTaskById(id: Long): Boolean

    fun existsTaskByIdAndUid(id: Long, uid: Long): Boolean

    fun getTaskById(id: Long): Task?

    @Query(value = "SELECT * FROM task_table WHERE id in ?1", nativeQuery = true)
    fun getTasksByIds(ids: Collection<Long>): MutableList<Task>

    @Query(
        value = """
        SELECT tt.* from task_table_labels tl 
           INNER JOIN task_table tt ON tl.task_id = tt.id 
        WHERE tt.uid = ?1 and tl.labels_id = ?2
    """, nativeQuery = true
    )
    fun getTasksByUidAndLabelContains(uid: Long, labelId: Long): MutableList<Task>

    @Query(value = "SELECT * FROM task_table WHERE gid IS NULL", nativeQuery = true)
    fun getRootTasks(): MutableList<Task>

    @Query(value = "SELECT * FROM task_table WHERE uid = ?1", nativeQuery = true)
    fun getUserTasks(uid: Long): MutableList<Task>

    @Query(value = "SELECT * FROM task_table WHERE uid = ?1 AND is_done = ?2", nativeQuery = true)
    fun getTasksByUidAndDone(uid: Long, isDone: Boolean): MutableList<Task>
}