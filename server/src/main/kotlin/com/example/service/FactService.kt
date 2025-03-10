package com.example.service

import com.example.model.FactDto
import com.example.model.FactDtoWithStats
import io.smallrye.mutiny.Uni

interface FactService {
    fun getAndCacheRandomFact(): Uni<FactDto>
    fun getCachedFact(id: String): Uni<FactDto>
    fun getAllCachedFacts(): Uni<List<FactDtoWithStats>>
}