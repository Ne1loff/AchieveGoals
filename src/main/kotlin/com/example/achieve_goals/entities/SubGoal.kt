package com.example.achieve_goals.entities

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*

@Entity
data class SubGoal(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    val title: String,

    val description: String?,

    val done: Boolean,

    @ManyToOne
    val parent: Goal,

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    val createdAt: Date,

    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    val updatedAt: Date,

    @Temporal(TemporalType.TIMESTAMP)
    val deadline: Date
)
