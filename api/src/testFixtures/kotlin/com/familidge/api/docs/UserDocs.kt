package com.familidge.api.docs

import com.familidge.api.domain.user.dto.request.SignInRequest
import com.familidge.api.domain.user.dto.response.SignInResponse
import com.familidge.api.util.bodyDesc
import com.familidge.api.util.fields

object UserDocs {
    inline fun <reified T> getFields() =
        when (T::class) {
            SignInRequest::class ->
                fields<SignInRequest> {
                    listOf(
                        ::token bodyDesc "토큰",
                        ::provider bodyDesc "OAuth2 제공자"
                    )
                }
            SignInResponse::class ->
                fields<SignInResponse> {
                    listOf(
                        ::isNew bodyDesc "신규 회원 여부",
                        ::email bodyDesc "이메일",
                        ::signUpToken bodyDesc "회원가입 토큰",
                        ::accessToken bodyDesc "액세스 토큰",
                        ::refreshToken bodyDesc "리프레시 토큰"
                    )
                }
            else -> throw NoSuchElementException()
        }
}
