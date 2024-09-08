package com.familidge.api.config

import com.familidge.api.fixture.jwtProvider
import com.familidge.api.global.config.SecurityConfiguration
import com.familidge.api.global.jwt.security.JwtFilter
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain

@TestConfiguration
class SecurityTestConfiguration {
    @Bean
    fun filterChain(http: ServerHttpSecurity): SecurityWebFilterChain =
        SecurityConfiguration()
            .filterChain(http, jwtFilter())

    @Bean
    fun jwtFilter(): JwtFilter = JwtFilter(jwtProvider)
}
