package com.familidge.api.domain.user.controller

import com.familidge.api.domain.user.dto.request.SignInRequest
import com.familidge.api.domain.user.dto.response.SignInResponse
import com.familidge.application.domain.user.port.`in`.SignInUseCase
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/auth")
@RestController
class AuthController(
    private val signIn: SignInUseCase,
) {
    @PostMapping("/sign-in")
    suspend fun signIn(
        @RequestBody
        request: SignInRequest
    ): SignInResponse =
        SignInResponse(signIn(request.toCommand()))
}
