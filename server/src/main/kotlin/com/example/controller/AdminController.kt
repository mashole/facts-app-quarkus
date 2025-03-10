import com.example.model.FactDtoWithStats
import com.example.service.FactService
import jakarta.inject.Inject
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path

@Path("/admin/statistics")
class AdminController() {

    @Inject lateinit var factService: FactService

    @GET
    fun getStatistics(): List<FactDtoWithStats> {
        return factService.getAllCachedFacts()
    }
}