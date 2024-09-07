package com.familidge.api.global.jwt.security

import com.familidge.application.global.jwt.core.JwtProvider
import io.jsonwebtoken.Claims
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

data class JwtAuthentication(
    val id: Int,
    val roles: Set<GrantedAuthority>
) : Authentication {
    companion object {
        operator fun invoke(claims: Claims): JwtAuthentication =
            with(claims) {
                JwtAuthentication(
                    id = get("id") as Int,
                    roles = (get("roles") as String)
                        .split(",")
                        .map(::SimpleGrantedAuthority)
                        .toHashSet()
                )
            }
    }

    override fun getName(): String {
        throw UnsupportedOperationException("JwtAuthentication does not include name.")
    }

    override fun getAuthorities(): Set<GrantedAuthority> = roles

    override fun getCredentials() {
        throw UnsupportedOperationException("JwtAuthentication does not support credential.")
    }

    override fun getDetails() {
        throw UnsupportedOperationException("JwtAuthentication does not support details.")
    }

    override fun getPrincipal() {
        throw UnsupportedOperationException("JwtAuthentication does not support principal.")
    }

    override fun isAuthenticated(): Boolean = true

    override fun setAuthenticated(isAuthenticated: Boolean) {
        throw UnsupportedOperationException("Cannot change the authenticated state of JwtAuthentication")
    }
}

fun JwtProvider.getAuthentication(token: String): JwtAuthentication =
    parser.parseClaimsJws(token)
        .run { JwtAuthentication(body) }
