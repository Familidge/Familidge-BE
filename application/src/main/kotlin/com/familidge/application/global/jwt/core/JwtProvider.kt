package com.familidge.application.global.jwt.core

import com.familidge.domain.domain.user.model.User
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.util.*
import javax.crypto.SecretKey
import kotlin.time.Duration

class JwtProvider(
    private val secretKey: SecretKey,
    private val accessTokenExpire: Duration,
    private val refreshTokenExpire: Duration
) {
    val parser =
        Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()!!

    fun createTokens(user: User): Pair<String, String> =
        mapOf(
            "id" to user.id,
            "roles" to "MEMBER"
        ).let { createAccessToken(it) to createRefreshToken(it) }

    private fun createAccessToken(claims: Map<String, *>): String =
        createToken(claims, accessTokenExpire)

    private fun createRefreshToken(claims: Map<String, *>): String =
        createToken(claims, refreshTokenExpire)

    private fun createToken(claims: Map<String, *>, expire: Duration): String {
        val now = Date()

        return Jwts.builder()
            .setIssuedAt(now)
            .setClaims(claims)
            .setExpiration(Date(now.time + expire.inWholeMilliseconds))
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact()
    }
}
