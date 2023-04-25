package com.example.achieve_goals.dto

data class UserSettingsDto(
    val uid: Long,

    var language: String?,

    var startedPage: String?,

    var timeFormat: String?,

    var dateFormat: String?,

    var theme: String?,

    var autoDarkTheme: Boolean?
)