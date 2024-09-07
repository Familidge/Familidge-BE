package com.familidge.infrastructure.global.redis.core

import com.familidge.infrastructure.global.redis.repository.CoroutineRedisRepositoryProxy
import com.familidge.infrastructure.global.util.coroutineScope
import com.familidge.infrastructure.global.util.minus
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.flow.Flow
import org.aopalliance.intercept.MethodInterceptor
import org.aopalliance.intercept.MethodInvocation
import org.springframework.data.redis.core.ReactiveRedisTemplate
import kotlin.coroutines.Continuation
import kotlin.reflect.KFunction
import kotlin.reflect.jvm.kotlinFunction
import kotlin.time.Duration.Companion.milliseconds

class CoroutineRedisRepositoryInterceptor(
    private val entityType: Class<*>,
    private val redisTemplate: ReactiveRedisTemplate<String, String>,
    private val objectMapper: ObjectMapper
) : MethodInterceptor {
    private val repositoryProxy = CoroutineRedisRepositoryProxy(
        entityType = entityType,
        redisTemplate = redisTemplate,
        objectMapper = objectMapper
    )

    override fun invoke(invocation: MethodInvocation): Any =
        with(invocation) {
            arguments.lastOrNull { it is Continuation<*> }
                ?.let {
                    (it as Continuation<Any?>)
                        .coroutineScope { coInvoke(method.kotlinFunction!!, (arguments - it).toList()) }
                }
                ?: invoke(method.kotlinFunction!!, arguments.toList())
        }

    private suspend fun coInvoke(function: KFunction<*>, parameters: List<*>): Any? =
        when (function.name) {
            "findByKey" -> repositoryProxy.findByKey(parameters[0].toString())
            "save" -> {
                val value = parameters[0] as Any

                when (parameters.size) {
                    1 -> repositoryProxy.save(value)
                    2 -> repositoryProxy.save(value, (parameters[1] as Long).milliseconds)
                    else -> throw UnsupportedOperationException()
                }
            }
            "deleteByKey" -> repositoryProxy.deleteByKey(parameters[0].toString())
            else -> throw UnsupportedOperationException()
        }

    private fun invoke(function: KFunction<*>, parameters: List<*>): Flow<*> =
        when (function.name) {
            else -> throw UnsupportedOperationException()
        }
}
