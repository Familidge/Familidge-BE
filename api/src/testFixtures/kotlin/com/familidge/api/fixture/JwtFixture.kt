package com.familidge.api.fixture

import com.familidge.api.global.config.JwtConfiguration
import com.familidge.api.global.jwt.security.JwtAuthentication
import com.familidge.domain.fixture.ID
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

val SECRET_KEY = (1..100).map { ('a'..'z').random() }.joinToString("")
const val ACCESS_TOKEN_EXPIRE = 5L
const val REFRESH_TOKEN_EXPIRE = 10L
val ROLES = setOf(SimpleGrantedAuthority("USER"))
val jwtProvider = JwtConfiguration()
    .jwtProvider(SECRET_KEY, ACCESS_TOKEN_EXPIRE, REFRESH_TOKEN_EXPIRE)

fun createJwtAuthentication(
    id: Int = ID,
    roles: Set<GrantedAuthority> = ROLES
): JwtAuthentication =
    JwtAuthentication(
        id = id,
        roles = roles
    )
