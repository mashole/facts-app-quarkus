package com.example.service

import FactEntity
import com.example.model.FactEntityWithStats

interface FactCacheService {
    fun cacheFact(fact: FactEntity)
    fun getCachedFact(id: String): FactEntityWithStats?
    fun getAllCachedFacts(): List<FactEntityWithStats>
    fun incrementAccessCount(id: String)
}
