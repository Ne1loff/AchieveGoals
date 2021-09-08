package com.example.achieve_goals.entities

import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "country")
data class Locality(

    @Id
    val id: Long,

    val name: String,

    @Column(name = "crt_date")
    val create_data: Date
)
