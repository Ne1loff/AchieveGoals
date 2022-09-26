package com.example.achieve_goals.repository

import com.example.achieve_goals.entities.UserSettings
import org.springframework.data.jpa.repository.JpaRepository

interface UserSettingsRepository : JpaRepository<UserSettings, Long> {
    fun getUserSettingsByUid(uid: Long): UserSettings?
}