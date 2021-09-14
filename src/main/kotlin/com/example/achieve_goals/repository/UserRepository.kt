package com.example.achieve_goals.repository

import com.example.achieve_goals.dto.UserDTO
import com.example.achieve_goals.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CrudRepository<User, Long> {

    fun findUserById(id: Long) : UserDTO
    fun findUserByEmail(email: String) : UserDTO
    fun findUserByUsernameSalt(username: String) : User

    fun existsUserByEmail(email: String) : Boolean
    fun existsUserByAdminIsTrue() : Boolean
}