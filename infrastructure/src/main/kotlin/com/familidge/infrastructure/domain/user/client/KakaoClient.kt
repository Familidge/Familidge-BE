package com.familidge.infrastructure.domain.user.client

import com.familidge.infrastructure.global.annotation.Client
import org.springframework.http.HttpHeaders
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody

@Client
class KakaoClient(
    private val webClient: WebClient
) : OAuth2Client {
    override suspend fun getEmail(token: String): String =
        webClient.get()
            .uri("https://kapi.kakao.com/v2/user/me")
            .header(HttpHeaders.AUTHORIZATION, "Bearer $token")
            .retrieve()
            .awaitBody<Map<String, *>>()
            .let { (it["kakao_account"] as Map<*, *>)["email"] as String }
}
