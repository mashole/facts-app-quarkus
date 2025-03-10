package com.example.service

import FactEntity
import com.example.controller.FactsController
import com.example.model.FactEntityWithStats
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import org.jboss.logging.Logger
import java.util.concurrent.ConcurrentHashMap

@ApplicationScoped
class FactCacheServiceImpl : FactCacheService {
    private val cache: MutableMap<String, FactEntityWithStats> = ConcurrentHashMap()
    private val logger: Logger = Logger.getLogger(FactsController::class.java)

    override fun cacheFact(fact: FactEntity): Uni<Void> {
        cache.compute(fact.id) { _, existingFact ->
            if (existingFact != null) {
                existingFact.accessCount += 1
                logger.info("Fact with ID: ${fact.id} already exists, incremented access count to ${existingFact.accessCount}")
                existingFact
            } else {
                logger.info("New fact cached with ID: ${fact.id}")
                FactEntityWithStats(fact)
            }
        }
        return Uni.createFrom().voidItem()
    }

    override fun getCachedFact(id: String): Uni<FactEntityWithStats?> {
        return Uni.createFrom().item(cache[id])
    }

    override fun getAllCachedFacts(): Uni<List<FactEntityWithStats>> {
        return Uni.createFrom().item {
            logger.info("Fetching all cached facts, size: ${cache.size}")
            cache.values.toList()
        }
    }

    override fun incrementAccessCount(id: String): Uni<Void> {
        cache[id]?.let { fact ->
            fact.accessCount += 1
            logger.info("Incremented access count for ${id}: ${fact.accessCount}")
        }
        return Uni.createFrom().voidItem()
    }
}