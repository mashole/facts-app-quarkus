package com.example.controller

import FactNotFoundException
import com.example.model.FactDto
import com.example.service.FactServiceImpl
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.jboss.logging.Logger

@Path("/facts")
@Produces(MediaType.APPLICATION_JSON)
class FactsController(private val factService: FactServiceImpl) {

    private val logger: Logger = Logger.getLogger(FactsController::class.java)

    @POST
    fun getAndCacheRandomFact(): FactDto {
        return factService.getAndCacheRandomFact()
    }

    @GET
    @Path("/{id}")
    fun getFactById(@PathParam("id") id: String): Response {
        try {
            val fact = factService.getCachedFact(id)
            return Response.ok(fact).build()
        } catch (e: FactNotFoundException) {
            logger.warn(e.message)
            return Response.status(Response.Status.NOT_FOUND).entity("Fact not found").build()
        }
    }

    @GET
    fun getAllFacts(): List<FactDto> {
        return factService.getAllCachedFacts().map { entity -> entity.fact }
    }
}
