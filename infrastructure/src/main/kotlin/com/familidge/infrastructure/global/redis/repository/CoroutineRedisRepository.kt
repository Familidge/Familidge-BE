package com.familidge.infrastructure.global.redis.repository

import org.springframework.data.repository.NoRepositoryBean
import kotlin.time.Duration

@NoRepositoryBean
interface CoroutineRedisRepository<V, K> {
    suspend fun findByKey(key: K): V?

    suspend fun save(value: V): V

    suspend fun save(value: V, ttl: Duration): V

    suspend fun deleteByKey(key: K): Boolean
}
