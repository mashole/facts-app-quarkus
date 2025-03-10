package com.example.service

import FactEntity
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FactCacheServiceImplTest {

    var factCacheService: FactCacheServiceImpl = FactCacheServiceImpl()

    private lateinit var fact: FactEntity

    @BeforeEach
    fun setUp() {
        fact = FactEntity("123", "Random fact", "source", "https://source.com", "en ")
    }

    @Test
    fun `test cacheFact when fact is new`() {
        val cacheSizeBefore = factCacheService.getAllCachedFacts().await().indefinitely().size

        factCacheService.cacheFact(fact)

        val cacheSizeAfter = factCacheService.getAllCachedFacts().await().indefinitely().size
        assertEquals(cacheSizeBefore + 1, cacheSizeAfter)

        assertNotNull(factCacheService.getCachedFact(fact.id))
    }

    @Test
    fun `test cacheFact when fact already cached`() {
        factCacheService.cacheFact(fact)

        val accessCountBefore = factCacheService.getCachedFact(fact.id).await()?.indefinitely()?.accessCount
        factCacheService.cacheFact(fact)

        val accessCountAfter = factCacheService.getCachedFact(fact.id).await()?.indefinitely()?.accessCount
        assertNotNull(accessCountAfter)
        assertEquals(accessCountBefore!! + 1, accessCountAfter)
    }

    @Test
    fun `test incrementAccessCount`() {
        factCacheService.cacheFact(fact)

        val accessCountBefore = factCacheService.getCachedFact(fact.id).await()?.indefinitely()?.accessCount
        factCacheService.incrementAccessCount(fact.id)

        val accessCountAfter = factCacheService.getCachedFact(fact.id).await()?.indefinitely()?.accessCount
        assertNotNull(accessCountAfter)
        assertEquals(accessCountBefore!! + 1, accessCountAfter)
    }

    @Test
    fun `test getAllCachedFacts`() {
        val fact2 = FactEntity("124", "Another fact", "source", "https://source.com", "en")
        factCacheService.cacheFact(fact)
        factCacheService.cacheFact(fact2)

        val allFacts = factCacheService.getAllCachedFacts()
        assertEquals(2, allFacts.await().indefinitely().size)
    }

    @Test
    fun `test getCachedFact`() {
        factCacheService.cacheFact(fact)

        val fetchedFact = factCacheService.getCachedFact(fact.id)
        assertNotNull(fetchedFact)
        assertEquals(fact.id, fetchedFact.await()?.indefinitely()?.fact?.id)
    }
}
