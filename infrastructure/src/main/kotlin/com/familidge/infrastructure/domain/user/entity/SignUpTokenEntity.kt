package com.familidge.infrastructure.domain.user.entity

import com.familidge.domain.domain.user.model.SignUpToken
import com.familidge.domain.domain.user.model.enum.Provider
import com.familidge.infrastructure.global.common.Entity
import com.familidge.infrastructure.global.redis.annotation.Key
import org.springframework.data.redis.core.RedisHash

@RedisHash
data class SignUpTokenEntity(
    @Key
    val email: String,
    val content: String,
    val provider: Provider
) : Entity<SignUpToken> {
    companion object {
        operator fun invoke(signUpToken: SignUpToken): SignUpTokenEntity =
            with(signUpToken) {
                SignUpTokenEntity(
                    email = email,
                    content = content,
                    provider = provider
                )
            }
    }

    override fun toDomain(): SignUpToken =
        SignUpToken(
            email = email,
            content = content,
            provider = provider
        )
}
