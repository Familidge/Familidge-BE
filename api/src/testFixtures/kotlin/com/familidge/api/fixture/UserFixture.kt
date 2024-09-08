package com.familidge.api.fixture

import com.familidge.api.domain.user.dto.request.SignInRequest
import com.familidge.domain.domain.user.model.enum.Provider
import com.familidge.domain.fixture.PROVIDER
import com.familidge.domain.fixture.TOKEN

fun createSignInRequest(
    token: String = TOKEN,
    provider: Provider = PROVIDER
): SignInRequest =
    SignInRequest(
        token = token,
        provider = provider
    )
