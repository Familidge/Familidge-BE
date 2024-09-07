package com.familidge.infrastructure.global.redis.core

import com.familidge.infrastructure.global.redis.repository.CoroutineRedisRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.aop.framework.ProxyFactory
import org.springframework.beans.factory.FactoryBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.ReactiveRedisTemplate
import java.lang.reflect.ParameterizedType

class CoroutineRedisRepositoryFactoryBean<T : CoroutineRedisRepository<*, *>>(
    private val repositoryType: Class<T>
) : FactoryBean<T> {
    @Autowired
    private lateinit var redisTemplate: ReactiveRedisTemplate<String, String>

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    private val entityType by lazy {
        (repositoryType.genericInterfaces[0] as ParameterizedType)
            .actualTypeArguments
            .first() as Class<*>
    }

    override fun getObject(): T =
        ProxyFactory.getProxy(
            repositoryType,
            CoroutineRedisRepositoryInterceptor(
                entityType = entityType,
                redisTemplate = redisTemplate,
                objectMapper = objectMapper
            )
        )

    override fun getObjectType(): Class<T> = repositoryType
}
