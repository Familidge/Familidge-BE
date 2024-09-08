package com.familidge.domain.domain.user.model

import com.familidge.domain.domain.user.model.enum.Provider

data class SignUpToken(
    val email: String,
    val content: String,
    val provider: Provider
)
