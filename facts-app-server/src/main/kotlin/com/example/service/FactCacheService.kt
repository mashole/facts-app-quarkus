package com.example.service

import FactEntity
import com.example.model.FactEntityWithStats
import io.smallrye.mutiny.Uni

interface FactCacheService {
    fun cacheFact(fact: FactEntity): Uni<Void>
    fun getCachedFact(id: String): Uni<FactEntityWithStats?>
    fun getAllCachedFacts(): Uni<List<FactEntityWithStats>>
    fun incrementAccessCount(id: String): Uni<Void>
}