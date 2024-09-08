package com.familidge.infrastructure.domain.user.adapter

import com.familidge.application.domain.user.port.out.GetUserByEmailPort
import com.familidge.domain.domain.user.model.User
import com.familidge.infrastructure.domain.user.repository.UserRepository
import com.familidge.infrastructure.global.annotation.Adapter

@Adapter
class GetUserByEmailAdapter(
    private val userRepository: UserRepository
) : GetUserByEmailPort {
    override suspend operator fun invoke(email: String): User? =
        userRepository.findByEmail(email)
            ?.toDomain()
}
