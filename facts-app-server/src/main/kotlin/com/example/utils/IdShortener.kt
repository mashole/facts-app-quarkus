package com.example.utils

import java.math.BigInteger
import java.security.MessageDigest

class IdShortener {
    companion object {
        fun shorten(id: String): String {
            val digest = MessageDigest.getInstance("SHA-256").digest(id.toByteArray())
            val bigInt = BigInteger(1, digest.copyOf(8)) // Use first 8 bytes for better uniqueness
            return bigInt.toString(36).padStart(6, '0').take(6)
        }
    }

}