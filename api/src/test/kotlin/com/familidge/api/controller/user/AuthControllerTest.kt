package com.familidge.api.controller.user

import com.familidge.api.common.ControllerTest
import com.familidge.api.docs.UserDocs
import com.familidge.api.domain.user.controller.AuthController
import com.familidge.api.domain.user.dto.request.SignInRequest
import com.familidge.api.domain.user.dto.response.SignInResponse
import com.familidge.api.fixture.createSignInRequest
import com.familidge.api.util.document
import com.familidge.api.util.expectStatus
import com.familidge.application.domain.user.port.`in`.SignInUseCase
import com.familidge.application.fixture.createSignInResult
import com.ninjasquad.springmockk.MockkBean
import io.mockk.coEvery
import org.springframework.context.annotation.Import
import org.springframework.test.web.reactive.server.expectBody

@Import(AuthController::class)
class AuthControllerTest : ControllerTest() {
    @MockkBean
    private lateinit var signIn: SignInUseCase

    init {
        describe("signIn()은") {
            context("유효한 OAuth2 관련 토큰이 주어진 경우") {
                coEvery { signIn(any()) } returns createSignInResult()

                it("상태 코드 200과 SignInResponse를 반환한다.") {
                    webClient.post()
                        .uri("/auth/sign-in")
                        .bodyValue(createSignInRequest())
                        .exchange()
                        .expectStatus(200)
                        .expectBody<SignInResponse>()
                        .document("OAuth2 액세스 토큰을 통한 로그인 성공(200)") {
                            requestBody(UserDocs.getFields<SignInRequest>())
                            responseBody(UserDocs.getFields<SignInResponse>())
                        }
                }
            }
        }
    }
}
