package com.example.service

import com.example.model.FactDto
import com.example.model.FactDtoWithStats

interface FactService {
    fun getAndCacheRandomFact(): FactDto
    fun getCachedFact(id: String): FactDto?
    fun getAllCachedFacts(): List<FactDtoWithStats>
}