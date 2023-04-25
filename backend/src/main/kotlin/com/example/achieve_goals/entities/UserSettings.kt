package com.example.achieve_goals.entities

import org.hibernate.Hibernate
import javax.persistence.*

@Entity
@Table(name = "user_settings_table")
data class UserSettings(

    @Id
    @JoinTable(
        name = "user_table",
        joinColumns = [JoinColumn(name = "id")]
    )
    val uid: Long,

    @Column(name = "user_language")
    var language: String,

    @Column(name = "started_page")
    var startedPage: String,

    @Column(name = "time_format")
    var timeFormat: String,

    @Column(name = "date_format")
    var dateFormat: String,

    @Column(name = "theme")
    var theme: String,

    @Column(name = "auto_dark_theme")
    var autoDarkTheme: Boolean
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as UserSettings

        return uid == other.uid
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(uid = $uid , language = $language , startedPage = $startedPage , timeFormat = $timeFormat , dateFormat = $dateFormat , theme = $theme )"
    }
}
