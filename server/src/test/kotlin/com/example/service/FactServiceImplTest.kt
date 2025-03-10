
import com.example.client.RemoteFactsClient
import com.example.model.FactEntityWithStats
import com.example.service.FactCacheServiceImpl
import com.example.service.FactServiceImpl
import jakarta.ws.rs.WebApplicationException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class FactServiceImplTest {

    @Mock
    private lateinit var remoteFactsClient: RemoteFactsClient

    @Mock
    private lateinit var factCacheService: FactCacheServiceImpl

    @InjectMocks
    private lateinit var factService: FactServiceImpl

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
        factService.factCacheService = factCacheService
    }

    @Test
    fun `getAndCacheRandomFact should fetch and cache fact`() {
        val factEntity = FactEntity("123", "A random fact", "source", "https://source.com", "en")
        `when`(remoteFactsClient.getRandomFact()).thenReturn(factEntity)

        val result = factService.getAndCacheRandomFact()


        assertNotNull(result)
        assertEquals(factEntity.text, result.text)
        verify(factCacheService, times(1)).cacheFact(factEntity)
    }

    @Test
    fun `getAndCacheRandomFact should throw exception when API fails`() {
        `when`(remoteFactsClient.getRandomFact()).thenThrow(RuntimeException("API Error"))

        val exception = assertThrows<WebApplicationException> {
            factService.getAndCacheRandomFact()
        }

        assertEquals("Failed to fetch fact from external service", exception.message)
    }

    @Test
    fun `getCachedFact should return cached fact`() {
        val factEntity = FactEntityWithStats(FactEntity("123", "A random fact", "source", "https://source.com", "en"), 1)
        `when`(factCacheService.getCachedFact("123")).thenReturn(factEntity)

        val result = factService.getCachedFact("123")

        assertNotNull(result)
        assertEquals(factEntity.fact.text, result?.text)
        verify(factCacheService, times(1)).incrementAccessCount("123")
    }

    @Test
    fun `getCachedFact should throw FactNotFoundException when fact is missing`() {
        `when`(factCacheService.getCachedFact("999")).thenReturn(null)

        val exception = assertThrows<FactNotFoundException> {
            factService.getCachedFact("999")
        }

        assertEquals("Fact with ID 999 not found", exception.message)
    }

    @Test
    fun `getAllCachedFacts should return list of facts`() {

        val facts = listOf(
            FactEntityWithStats(FactEntity("123", "Fact 1", "source", "https://source.com", "en"), 2),
            FactEntityWithStats(FactEntity("456", "Fact 2", "source", "https://source.com", "en"), 5)
        )

        `when`(factCacheService.getAllCachedFacts()).thenReturn(facts)

        val result = factService.getAllCachedFacts()

        assertEquals(2, result.size)
        assertEquals("Fact 1", result[0].fact.text)
        assertEquals(5, result[1].accessCount)
    }
}
