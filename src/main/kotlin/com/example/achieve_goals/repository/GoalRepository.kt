package com.example.achieve_goals.repository

import com.example.achieve_goals.entities.Goal
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface GoalRepository : CrudRepository<Goal, Long> {

    fun getGoalsByGid(gid: Long): MutableList<Goal>

    fun getGoalsByUidOrderByCreatedAt(uid: Long): MutableList<Goal>

    fun existsGoalById(id: Long): Boolean

    fun existsGoalByIdAndUid(id: Long, uid: Long): Boolean

    fun getGoalById(id: Long): Goal?

    @Query(value = "SELECT * FROM goal_table WHERE gid IS NULL", nativeQuery = true)
    fun getRootGoals(): MutableList<Goal>

    @Query(value = "SELECT * FROM goal_table WHERE uid = ?1", nativeQuery = true)
    fun getUserGoals(uid: Long): MutableList<Goal>

    @Query(value = "SELECT * FROM goal_table WHERE uid = ?1 AND is_done = ?2", nativeQuery = true)
    fun getGoalsByGidAndDone(gid: Long, isDone: Boolean): MutableList<Goal>
}