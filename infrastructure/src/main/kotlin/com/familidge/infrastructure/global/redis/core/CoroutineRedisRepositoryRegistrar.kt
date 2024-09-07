package com.familidge.infrastructure.global.redis.core

import com.familidge.infrastructure.global.redis.annotation.EnableReactiveRedisRepositories
import org.springframework.beans.factory.support.BeanDefinitionRegistry
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar
import org.springframework.core.type.AnnotationMetadata
import org.springframework.stereotype.Component

@Component
class CoroutineRedisRepositoryRegistrar : ImportBeanDefinitionRegistrar {
    override fun registerBeanDefinitions(metadata: AnnotationMetadata, registry: BeanDefinitionRegistry) {
        EnableReactiveRedisRepositories(metadata)
            .basePackages
            .forEach {
                CoroutineRedisRepositoryScanner(registry)
                    .findCandidateComponents(it)
            }
    }
}
