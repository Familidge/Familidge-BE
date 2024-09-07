package com.familidge.infrastructure.global.config

import com.familidge.infrastructure.global.redis.annotation.EnableReactiveRedisRepositories
import org.springframework.context.annotation.Configuration

@EnableReactiveRedisRepositories(basePackages = ["com.familidge.infrastructure"])
@Configuration
class RedisConfiguration
