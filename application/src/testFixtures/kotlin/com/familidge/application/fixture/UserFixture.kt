package com.familidge.application.fixture

import com.familidge.application.domain.user.dto.command.SignInCommand
import com.familidge.application.domain.user.dto.result.SignInResult
import com.familidge.domain.domain.user.model.enum.Provider
import com.familidge.domain.fixture.EMAIL
import com.familidge.domain.fixture.PROVIDER
import com.familidge.domain.fixture.TOKEN

const val IS_NEW = true

fun createSignInCommand(
    token: String = TOKEN,
    provider: Provider = PROVIDER
): SignInCommand =
    SignInCommand(
        token = token,
        provider = provider
    )

fun createSignInResult(
    isNew: Boolean = IS_NEW,
    email: String = EMAIL,
    signUpToken: String? = TOKEN,
    accessToken: String? = TOKEN,
    refreshToken: String? = TOKEN,
): SignInResult =
    SignInResult(
        isNew = isNew,
        email = email,
        signUpToken = signUpToken,
        accessToken = accessToken,
        refreshToken = refreshToken
    )
