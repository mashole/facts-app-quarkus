package com.example.service

import FactNotFoundException
import com.example.client.RemoteFactsClient
import com.example.controller.FactsController
import com.example.model.FactDto
import com.example.model.FactDtoWithStats
import io.smallrye.mutiny.Uni
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

    override fun getAndCacheRandomFact(): Uni<FactDto> {
        logger.info("Fetching a new fact from the API...")
        return remoteFactsClient.getRandomFact().onItem().transform { factEntity ->
            factCacheService.cacheFact(factEntity)
            FactDto.fromEntity(factEntity)
        }
            .onFailure().recoverWithUni { throwable ->
                logger.error("Failed to fetch fact from external service", throwable)
                Uni.createFrom().failure(
                    WebApplicationException(
                        "Failed to fetch fact from external service",
                        Response.Status.INTERNAL_SERVER_ERROR
                    )
                )
            }
    }

    override fun getCachedFact(id: String): Uni<FactDto> {
        return factCacheService.getCachedFact(id).onItem().transform { factEntity->
            if (factEntity == null) {
                throw FactNotFoundException("Fact with ID $id not found")
            }
            factCacheService.incrementAccessCount(id)
            logger.info("Fact ID: $id accessed, new count: ${factEntity.accessCount}")
            FactDto.fromEntity(factEntity.fact)
        }
        }

    override fun getAllCachedFacts(): Uni<List<FactDtoWithStats>> {
        return factCacheService.getAllCachedFacts().onItem().transform { entity ->
            entity.map { factEntity ->
                FactDtoWithStats.fromEntity(factEntity)
            }
        }
    }
}