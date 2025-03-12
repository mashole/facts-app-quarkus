package com.example.model

import FactEntity
import com.example.utils.IdShortener

data class FactDto(
    val id: String,
    val text: String,
    val source: String?,
    val sourceUrl: String?,
    val permalink: String,
    val language: String
) {
    companion object {
        fun fromEntity(entity: FactEntity): FactDto {
            requireNotNull(entity) { "Fact entity must not be null" }
            return FactDto(
                id = IdShortener.shorten(entity.id),
                text = entity.text,
                source = entity.source,
                sourceUrl = entity.sourceUrl,
                permalink = entity.permalink,
                language = entity.language
            )
        }
    }
}
