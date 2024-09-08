package com.familidge.infrastructure.domain.user.repository

import com.familidge.infrastructure.domain.user.entity.UserEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CoroutineCrudRepository<UserEntity, Int> {
    suspend fun findByEmail(email: String): UserEntity?
}
