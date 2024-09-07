package com.familidge.api.global.jwt.security

import com.familidge.application.global.jwt.core.JwtProvider
import kotlinx.coroutines.reactor.ReactorContext
import kotlinx.coroutines.withContext
import org.springframework.http.HttpHeaders
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.web.server.CoWebFilter
import org.springframework.web.server.CoWebFilterChain
import org.springframework.web.server.ServerWebExchange

class JwtFilter(
    private val jwtProvider: JwtProvider
) : CoWebFilter() {
    override suspend fun filter(exchange: ServerWebExchange, chain: CoWebFilterChain) {
        val token = exchange.request.headers.getBearerToken()
        val authentication = runCatching { token?.let(jwtProvider::getAuthentication) }.getOrNull()
        val context = ReactorContext(ReactiveSecurityContextHolder.withAuthentication(authentication))

        withContext(context) {
            chain.filter(exchange)
        }
    }

    private fun HttpHeaders.getBearerToken(): String? =
        getFirst(HttpHeaders.AUTHORIZATION)
            ?.takeIf { it.startsWith("Bearer") }
            ?.run { substring(7) }
}
