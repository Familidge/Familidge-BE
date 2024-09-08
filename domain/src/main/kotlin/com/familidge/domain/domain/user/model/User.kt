package com.familidge.domain.domain.user.model

import com.familidge.domain.domain.user.model.enum.Provider
import com.familidge.domain.domain.user.model.enum.Type

data class User(
    val id: Int? = null,
    val email: String,
    val provider: Provider,
    val type: Type,
    val code: String
)
