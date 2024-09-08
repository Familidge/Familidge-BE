package com.familidge.infrastructure.domain.user.entity

import com.familidge.domain.domain.user.model.User
import com.familidge.domain.domain.user.model.enum.Provider
import com.familidge.domain.domain.user.model.enum.Type
import com.familidge.infrastructure.global.common.Entity
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("user")
data class UserEntity(
    @Id
    val id: Int? = null,
    val email: String,
    val provider: Provider,
    val type: Type,
    val code: String
) : Entity<User> {
    companion object {
        operator fun invoke(user: User): UserEntity =
            with(user) {
                UserEntity(
                    id = id,
                    email = email,
                    provider = provider,
                    type = type,
                    code = code
                )
            }
    }

    override fun toDomain(): User =
        User(
            id = id,
            email = email,
            provider = provider,
            type = type,
            code = code
        )
}
