package com.familidge.api.global.config

import com.familidge.api.global.jwt.security.JwtFilter
import com.familidge.application.global.jwt.core.JwtProvider
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import kotlin.time.Duration.Companion.minutes

@Configuration
class JwtConfiguration {
    @Bean
    fun jwtProvider(
        @Value("\${jwt.secretKey}")
        secretKey: String,
        @Value("\${jwt.accessTokenExpire}")
        accessTokenExpire: Long,
        @Value("\${jwt.refreshTokenExpire}")
        refreshTokenExpire: Long
    ): JwtProvider = JwtProvider(
        secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)),
        accessTokenExpire = accessTokenExpire.minutes,
        refreshTokenExpire = refreshTokenExpire.minutes
    )

    @Bean
    fun jwtFilter(jwtProvider: JwtProvider): JwtFilter = JwtFilter(jwtProvider)
}
