package com.familidge.infrastructure.global.redis.annotation

import com.familidge.infrastructure.global.redis.core.CoroutineRedisRepositoryRegistrar
import org.springframework.context.annotation.Import
import org.springframework.core.type.AnnotationMetadata
import java.lang.annotation.Inherited

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Inherited
@Import(CoroutineRedisRepositoryRegistrar::class)
annotation class EnableReactiveRedisRepositories(
    val basePackages: Array<String> = []
) {
    companion object {
        operator fun invoke(metadata: AnnotationMetadata): EnableReactiveRedisRepositories =
            EnableReactiveRedisRepositories(
                metadata.getAnnotationAttributes(EnableReactiveRedisRepositories::class.java.name)
                    ?.get(EnableReactiveRedisRepositories::basePackages.name) as Array<String>?
                    ?: emptyArray()
            )
    }
}
