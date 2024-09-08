package com.familidge.application.domain.user.dto.command

import com.familidge.domain.domain.user.model.enum.Provider

data class SignInCommand(
    val token: String,
    val provider: Provider
)
