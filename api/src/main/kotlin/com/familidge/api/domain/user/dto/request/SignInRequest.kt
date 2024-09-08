package com.familidge.api.domain.user.dto.request

import com.familidge.application.domain.user.dto.command.SignInCommand
import com.familidge.domain.domain.user.model.enum.Provider

data class SignInRequest(
    val token: String,
    val provider: Provider
) {
    fun toCommand(): SignInCommand =
        SignInCommand(
            token = token,
            provider = provider
        )
}
