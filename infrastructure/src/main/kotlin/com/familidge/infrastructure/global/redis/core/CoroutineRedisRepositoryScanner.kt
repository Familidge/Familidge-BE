package com.familidge.infrastructure.global.redis.core

import com.familidge.infrastructure.global.redis.repository.CoroutineRedisRepository
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition
import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.beans.factory.support.BeanDefinitionRegistry
import org.springframework.beans.factory.support.GenericBeanDefinition
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider
import org.springframework.core.type.filter.AssignableTypeFilter

class CoroutineRedisRepositoryScanner(
    private val registry: BeanDefinitionRegistry
) : ClassPathScanningCandidateComponentProvider(false) {
    init {
        addIncludeFilter(AssignableTypeFilter(CoroutineRedisRepository::class.java))
    }

    override fun isCandidateComponent(beanDefinition: AnnotatedBeanDefinition): Boolean =
        super.isCandidateComponent(beanDefinition) || beanDefinition.metadata.isInterface

    override fun findCandidateComponents(basePackage: String): MutableSet<BeanDefinition> =
        super.findCandidateComponents(basePackage)
            .onEach {
                val beanDefinition = it as GenericBeanDefinition
                val beanClassName = beanDefinition.beanClassName
                val factoryBeanDefinition = GenericBeanDefinition()
                    .apply {
                        setBeanClass(CoroutineRedisRepositoryFactoryBean::class.java)
                        constructorArgumentValues.addGenericArgumentValue(Class.forName(beanClassName))
                    }

                registry.registerBeanDefinition(beanClassName!!, factoryBeanDefinition)
            }
}
