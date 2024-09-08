package com.familidge.infrastructure.domain.user.adapter

import com.familidge.application.domain.user.port.out.GetEmailByTokenAndProviderPort
import com.familidge.domain.domain.user.model.enum.Provider
import com.familidge.infrastructure.domain.user.client.KakaoClient
import com.familidge.infrastructure.domain.user.client.NaverClient
import com.familidge.infrastructure.global.annotation.Adapter

@Adapter
class GetEmailByTokenAndProviderAdapter(
    private val kakaoClient: KakaoClient,
    private val naverClient: NaverClient
) : GetEmailByTokenAndProviderPort {
    override suspend operator fun invoke(token: String, provider: Provider): String =
        provider.getClient()
            .getEmail(token)

    private fun Provider.getClient() =
        when (this) {
            Provider.KAKAO -> kakaoClient
            Provider.NAVER -> naverClient
        }
}
