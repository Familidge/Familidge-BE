package com.familidge.application.domain.user.port.out

import com.familidge.domain.domain.user.model.User

fun interface GetUserByEmailPort {
    suspend operator fun invoke(email: String): User?
}
