package com.example.service

import FactNotFoundException
import com.example.client.RemoteFactsClient
import com.example.controller.FactsController
import com.example.model.FactDto
import com.example.model.FactDtoWithStats
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.ws.rs.WebApplicationException
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.rest.client.inject.RestClient
import org.jboss.logging.Logger


@ApplicationScoped
class FactServiceImpl(
    @RestClient private val remoteFactsClient: RemoteFactsClient
) : FactService {

    @Inject
    lateinit var factCacheService: FactCacheService

    private val logger: Logger = Logger.getLogger(FactsController::class.java)

    override fun getAndCacheRandomFact(): FactDto {
        try {
            logger.info("Fetching a new fact from the API...")
            val factEntity = remoteFactsClient.getRandomFact()

            factCacheService.cacheFact(factEntity)
            return FactDto.fromEntity(factEntity)
        } catch (e: Exception) {
            logger.error("Failed to fetch fact from external service", e)
            throw WebApplicationException(
                "Failed to fetch fact from external service",
                Response.Status.INTERNAL_SERVER_ERROR
            )
        }
    }

    override fun getCachedFact(id: String): FactDto? {
        val factEntity = factCacheService.getCachedFact(id)
            ?: throw FactNotFoundException("Fact with ID $id not found")

        factCacheService.incrementAccessCount(id)
        logger.info("Fact ID: $id accessed, new count: ${factEntity.accessCount}")
        return FactDto.fromEntity(factEntity.fact)
    }


    override fun getAllCachedFacts(): List<FactDtoWithStats> {
        return factCacheService.getAllCachedFacts().map { entity -> FactDtoWithStats.fromEntity(entity) }
    }
}