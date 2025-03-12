import com.fasterxml.jackson.annotation.JsonProperty

data class FactEntity(
    val id: String,
    val text: String,
    val source: String?,
    @JsonProperty("source_url")
    val sourceUrl: String?,
    val permalink: String,
    val language: String
)
