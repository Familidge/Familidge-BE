package com.familidge.application.domain.user.dto.result

data class SignInResult(
    val isNew: Boolean,
    val email: String,
    val signUpToken: String?,
    val accessToken: String?,
    val refreshToken: String?
)
