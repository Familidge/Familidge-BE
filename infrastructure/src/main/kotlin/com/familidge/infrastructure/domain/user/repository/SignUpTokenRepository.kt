package com.familidge.infrastructure.domain.user.repository

import com.familidge.infrastructure.domain.user.entity.SignUpTokenEntity
import com.familidge.infrastructure.global.redis.repository.CoroutineRedisRepository
import org.springframework.stereotype.Repository

@Repository
interface SignUpTokenRepository : CoroutineRedisRepository<SignUpTokenEntity, String>
