package com.example.client

import FactEntity
import io.smallrye.mutiny.Uni
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient

@Path("/api/v2/facts/random")
@RegisterRestClient(baseUri = "https://uselessfacts.jsph.pl")
interface RemoteFactsClient {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun getRandomFact(): Uni<FactEntity>
}