package com.example.achieve_goals.repository

import com.example.achieve_goals.entities.UserAvatar
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserAvatarRepository : JpaRepository<UserAvatar, Long> {
    fun getUserAvatarById(id: Long): UserAvatar
    fun deleteUserAvatarById(id: Long)
}