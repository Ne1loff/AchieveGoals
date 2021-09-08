package com.example.achieve_goals.repository

import com.example.achieve_goals.entities.Locality
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LocalityRepository : JpaRepository<Locality, Long> {
    fun getLocalityById(id: Long) : Locality
}