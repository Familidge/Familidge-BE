package com.familidge.application.domain.user.service

import com.familidge.application.domain.user.dto.command.SignInCommand
import com.familidge.application.domain.user.dto.result.SignInResult
import com.familidge.application.domain.user.port.`in`.SignInUseCase
import com.familidge.application.domain.user.port.out.GetEmailByTokenAndProviderPort
import com.familidge.application.domain.user.port.out.GetUserByEmailPort
import com.familidge.application.domain.user.port.out.SaveSignUpTokenPort
import com.familidge.application.global.jwt.core.JwtProvider
import com.familidge.domain.domain.user.model.SignUpToken
import org.springframework.stereotype.Service

@Service
class SignInService(
    private val getUserByEmail: GetUserByEmailPort,
    private val getEmailByTokenAndProvider: GetEmailByTokenAndProviderPort,
    private val saveSignUpToken: SaveSignUpTokenPort,
    private val jwtProvider: JwtProvider
) : SignInUseCase {
    override suspend operator fun invoke(command: SignInCommand): SignInResult =
        with(command) {
            val email = getEmailByTokenAndProvider(token, provider)
            val user = getUserByEmail(email)
            val signUpToken =
                if (user == null)
                    saveSignUpToken(
                        SignUpToken(
                            email = email,
                            content = token,
                            provider = provider
                        )
                    )
                else null
            val (accessToken, refreshToken) = user?.run(jwtProvider::createTokens) ?: (null to null)

            SignInResult(
                isNew = (user == null),
                email = email,
                signUpToken = signUpToken?.content,
                accessToken = accessToken,
                refreshToken = refreshToken
            )
        }
}
