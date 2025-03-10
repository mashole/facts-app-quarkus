package com.example.service

import FactEntity
import com.example.controller.FactsController
import com.example.model.FactEntityWithStats
import jakarta.enterprise.context.ApplicationScoped
import org.jboss.logging.Logger

@ApplicationScoped
class FactCacheServiceImpl : FactCacheService {
    private val cache: MutableMap<String, FactEntityWithStats> = HashMap()
    private val logger: Logger = Logger.getLogger(FactsController::class.java)

    override fun cacheFact(fact: FactEntity) {
        val existingFact = cache[fact.id]
        if (existingFact != null) {
            synchronized(existingFact) {
                existingFact.accessCount += 1
                logger.info("Fact with ID: ${fact.id} already exists, incremented access count to ${existingFact.accessCount}")
            }
        } else {
            cache[fact.id] = FactEntityWithStats(fact)
            logger.info("New fact cached with ID: ${fact.id}")
        }
    }

    override fun getCachedFact(id: String): FactEntityWithStats? = cache[id]

    override fun getAllCachedFacts(): List<FactEntityWithStats> {
        logger.info("Fetching all cached facts, size: ${cache.size}")
        return cache.values.toList()
    }

    override fun incrementAccessCount(id: String) {
        cache[id]?.let { fact ->
            synchronized(fact) {
                fact.accessCount += 1
                logger.info("Incremented access count for ${id}: ${fact.accessCount}")
            }
        }
    }
}
