package com.familidge.application.domain.user.port.out

import com.familidge.domain.domain.user.model.SignUpToken

fun interface SaveSignUpTokenPort {
    suspend operator fun invoke(signUpToken: SignUpToken): SignUpToken
}
