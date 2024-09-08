package com.familidge.domain.fixture

import com.familidge.domain.domain.user.model.SignUpToken
import com.familidge.domain.domain.user.model.User
import com.familidge.domain.domain.user.model.enum.Provider
import com.familidge.domain.domain.user.model.enum.Type

const val EMAIL = "earlgrey02@kakao.com"
val PROVIDER = Provider.KAKAO
val TYPE = Type.CHILD
const val CODE = "abcdefghi"
const val TOKEN = "dsdsfdfsldsfjkldsffsdlkjdsfjklfdskldfskfljljsdksldfj"

fun createUser(
    id: Int = ID,
    email: String = EMAIL,
    provider: Provider = PROVIDER,
    type: Type = TYPE,
    code: String = CODE
): User =
    User(
        id = id,
        email = email,
        provider = provider,
        type = type,
        code = code
    )

fun createSignUpToken(
    email: String = EMAIL,
    content: String = TOKEN,
    provider: Provider = PROVIDER
): SignUpToken =
    SignUpToken(
        email = email,
        content = content,
        provider = provider
    )
