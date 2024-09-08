package com.familidge.api.domain.user.dto.response

import com.familidge.application.domain.user.dto.result.SignInResult

data class SignInResponse(
    val isNew: Boolean,
    val email: String,
    val signUpToken: String?,
    val accessToken: String?,
    val refreshToken: String?
) {
    companion object {
        operator fun invoke(result: SignInResult): SignInResponse =
            with(result) {
                SignInResponse(
                    isNew = isNew,
                    email = email,
                    signUpToken = signUpToken,
                    accessToken = accessToken,
                    refreshToken = refreshToken
                )
            }
    }
}
