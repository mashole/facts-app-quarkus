package com.example.model

import FactEntity


data class FactEntityWithStats(
    val fact: FactEntity,
    var accessCount: Int = 1
)
