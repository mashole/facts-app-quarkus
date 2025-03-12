package com.example.model


data class FactDtoWithStats (
    val fact: FactDto,
    var accessCount: Int = 1
){
    companion object {
        fun fromEntity(entity: FactEntityWithStats): FactDtoWithStats {
            requireNotNull(entity.fact) { "Fact entity must not be null" }
            return FactDtoWithStats(
                FactDto.fromEntity(entity.fact),
                accessCount = entity.accessCount
            )
        }
    }
}