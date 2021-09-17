package com.example.achieve_goals.entities

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*

@Entity
@JsonIgnoreProperties(
    value = ["createdAt", "updatedAt"],
    allowGetters = true
)
@Table(name = "goal_table")
data class Goal(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @JoinTable(
        name = "user_table",
        joinColumns = [JoinColumn(name = "id")]
    )
    val uid: Long,

    var title: String,

    var description: String?,

    @Column(name = "is_done")
    var isDone: Boolean = false,

    val gid: Long? = null,

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @Column(name = "created_at")
    val createdAt: Date,

    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    @Column(name = "updated_at")
    var updatedAt: Date,

    @Temporal(TemporalType.TIMESTAMP)
    var deadline: Date
)
