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
data class Goal(

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    val id: Long,

    val title: String,

    val description: String?,

    val done: Boolean,

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    val createdAt: Date,

    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    val updatedAt: Date,

    @Temporal(TemporalType.TIMESTAMP)
    val deadline: Date
)
