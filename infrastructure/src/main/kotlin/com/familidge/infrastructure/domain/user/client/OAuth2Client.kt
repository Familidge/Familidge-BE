package com.familidge.infrastructure.domain.user.client

interface OAuth2Client {
    suspend fun getEmail(token: String): String
}
