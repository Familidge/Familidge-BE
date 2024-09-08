package com.familidge.infrastructure.global.redis.repository

import com.familidge.infrastructure.global.redis.annotation.Key
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.repository.NoRepositoryBean
import kotlin.time.Duration
import kotlin.time.toJavaDuration

@NoRepositoryBean
class CoroutineRedisRepositoryProxy(
    private val entityType: Class<*>,
    private val redisTemplate: ReactiveRedisTemplate<String, String>,
    private val objectMapper: ObjectMapper
) : CoroutineRedisRepository<Any, String> {
    override suspend fun findByKey(key: String): Any? =
        redisTemplate.opsForValue()
            .get(key)
            .map { objectMapper.readValue(it, entityType) }
            .awaitSingleOrNull()

    override suspend fun save(value: Any): Any =
        redisTemplate.opsForValue()
            .set(value.getKey(), objectMapper.writeValueAsString(value))
            .thenReturn(value)
            .awaitSingle()

    override suspend fun save(value: Any, ttl: Duration): Any =
        redisTemplate.opsForValue()
            .set(value.getKey(), objectMapper.writeValueAsString(value), ttl.toJavaDuration())
            .thenReturn(value)
            .awaitSingle()

    override suspend fun deleteByKey(key: String): Boolean =
        redisTemplate.opsForValue()
            .delete(key)
            .awaitSingle()

    private fun Any.getKey() =
        entityType.declaredFields
            .first { it.isAnnotationPresent(Key::class.java) }
            .apply { setAccessible(true) }
            .get(this)
            .toString()
}
