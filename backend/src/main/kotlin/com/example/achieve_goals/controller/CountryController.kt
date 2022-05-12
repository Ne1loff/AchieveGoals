package com.example.achieve_goals.controller

import com.example.achieve_goals.dto.CountryDTO
import com.example.achieve_goals.repository.LocalityRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/api/countries/"])
class CountryController(val localityRepository: LocalityRepository) {

    @GetMapping
    fun getCountryList(): MutableList<CountryDTO> {
        return localityRepository.getAllOrderByName().map { (id, name) -> CountryDTO(id, name)}.toMutableList()
    }
}