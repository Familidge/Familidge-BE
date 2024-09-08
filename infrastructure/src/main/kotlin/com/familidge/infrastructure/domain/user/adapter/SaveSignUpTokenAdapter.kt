package com.familidge.infrastructure.domain.user.adapter

import com.familidge.application.domain.user.port.out.SaveSignUpTokenPort
import com.familidge.domain.domain.user.model.SignUpToken
import com.familidge.infrastructure.domain.user.entity.SignUpTokenEntity
import com.familidge.infrastructure.domain.user.repository.SignUpTokenRepository
import com.familidge.infrastructure.global.annotation.Adapter
import kotlin.time.Duration.Companion.minutes

@Adapter
class SaveSignUpTokenAdapter(
    private val signUpTokenRepository: SignUpTokenRepository
) : SaveSignUpTokenPort {
    override suspend operator fun invoke(signUpToken: SignUpToken): SignUpToken =
        signUpTokenRepository.save(SignUpTokenEntity(signUpToken), 1.minutes)
            .toDomain()
}
