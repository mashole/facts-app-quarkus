package com.example.model


data class FactDtoWithStats (
    val fact: FactDto,
    var accessCount: Int = 1
){
    companion object {
        fun fromEntity(entity: FactEntityWithStats): FactDtoWithStats {
            requireNotNull(entity.fact) { "Fact entity must not be null" }
            return FactDtoWithStats(
                FactDto(
                    id = entity.fact.id,
                    text = entity.fact.text,
                    source = entity.fact.source,
                    sourceUrl = entity.fact.sourceUrl,
                    permalink = entity.fact.permalink,
                    language = entity.fact.language,
                ),
                accessCount = entity.accessCount
            )
        }
    }
}