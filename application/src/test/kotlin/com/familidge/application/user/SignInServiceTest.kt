package com.familidge.application.user

import com.familidge.application.domain.user.port.out.GetEmailByTokenAndProviderPort
import com.familidge.application.domain.user.port.out.GetUserByEmailPort
import com.familidge.application.domain.user.port.out.SaveSignUpTokenPort
import com.familidge.application.domain.user.service.SignInService
import com.familidge.application.fixture.createSignInCommand
import com.familidge.application.global.jwt.core.JwtProvider
import com.familidge.domain.fixture.EMAIL
import com.familidge.domain.fixture.TOKEN
import com.familidge.domain.fixture.createSignUpToken
import com.familidge.domain.fixture.createUser
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.mockk.coEvery
import io.mockk.mockk

class SignInServiceTest : BehaviorSpec() {
    private val getUserByEmail = mockk<GetUserByEmailPort>()
    private val getEmailByTokenAndProvider = mockk<GetEmailByTokenAndProviderPort>()
    private val saveSignUpToken = mockk<SaveSignUpTokenPort>()
    private val jwtProvider = mockk<JwtProvider>()

    private val signIn = SignInService(
        getUserByEmail = getUserByEmail,
        getEmailByTokenAndProvider = getEmailByTokenAndProvider,
        saveSignUpToken = saveSignUpToken,
        jwtProvider = jwtProvider
    )

    init {
        Given("해당 유저가 처음 서비스를 이용하는 경우") {
            coEvery { getEmailByTokenAndProvider(any(), any()) } returns EMAIL
            coEvery { getUserByEmail(any()) } returns null
            coEvery { saveSignUpToken(any()) } returns createSignUpToken()

            When("로그인을 시도하면") {
                val result = signIn(createSignInCommand())

                Then("회원가입을 하게 된다.") {
                    with(result) {
                        isNew.shouldBeTrue()
                        email shouldBeEqual EMAIL
                        signUpToken.shouldNotBeNull() shouldBeEqual TOKEN
                        accessToken.shouldBeNull()
                        refreshToken.shouldBeNull()
                    }
                }
            }
        }

        Given("해당 유저가 이미 가입한 경우") {
            val user = createUser()
                .also {
                    coEvery { getEmailByTokenAndProvider(any(), any()) } returns it.email
                    coEvery { getUserByEmail(any()) } returns it
                    coEvery { jwtProvider.createTokens(any()) } returns (TOKEN to TOKEN)
                }

            When("로그인을 시도하면") {
                val result = signIn(createSignInCommand())

                Then("성공적으로 로그인이 된다.") {
                    with(result) {
                        isNew.shouldBeFalse()
                        email shouldBeEqual user.email
                        signUpToken.shouldBeNull()
                        accessToken.shouldNotBeNull() shouldBeEqual TOKEN
                        refreshToken.shouldNotBeNull() shouldBeEqual TOKEN
                    }
                }
            }
        }
    }
}
