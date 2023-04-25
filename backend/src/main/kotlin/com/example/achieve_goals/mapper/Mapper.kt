package com.example.achieve_goals.mapper

interface Mapper<E, D> {
    fun toDto(entity: E): D
    fun toEntity(dto: D): E
}