package com.familidge.application.domain.user.port.out

import com.familidge.domain.domain.user.model.enum.Provider

fun interface GetEmailByTokenAndProviderPort {
    suspend operator fun invoke(token: String, provider: Provider): String
}
