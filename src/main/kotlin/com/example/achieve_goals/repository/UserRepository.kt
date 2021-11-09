package com.example.achieve_goals.repository

import com.example.achieve_goals.entities.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CrudRepository<User, Long> {

    fun findUserById(id: Long): User
    fun findUserByEmail(email: String): User?
    fun findUserByUsernameSalt(username: String): User?

    fun existsUserByEmail(email: String): Boolean
    fun existsUserByUsernameSalt(username: String): Boolean
    fun existsUserByAdminIsTrue(): Boolean
}