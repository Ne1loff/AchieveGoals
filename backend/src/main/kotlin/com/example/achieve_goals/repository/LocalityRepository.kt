package com.example.achieve_goals.repository

import com.example.achieve_goals.entities.Locality
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface LocalityRepository : JpaRepository<Locality, Long> {

    @Query("select * from country order by name", nativeQuery = true)
    fun getAllOrderByName(): List<Locality>

    fun getLocalityById(id: Long): Locality
}