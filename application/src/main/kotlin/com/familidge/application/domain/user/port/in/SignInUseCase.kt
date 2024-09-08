package com.familidge.application.domain.user.port.`in`

import com.familidge.application.domain.user.dto.command.SignInCommand
import com.familidge.application.domain.user.dto.result.SignInResult

fun interface SignInUseCase {
    suspend operator fun invoke(command: SignInCommand): SignInResult
}
