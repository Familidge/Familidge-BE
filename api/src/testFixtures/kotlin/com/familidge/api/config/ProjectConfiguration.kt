package com.familidge.api.config

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.extensions.Extension
import io.kotest.extensions.spring.SpringExtension
import org.springframework.boot.test.context.TestConfiguration

@TestConfiguration
class ProjectConfiguration : AbstractProjectConfig() {
    override val parallelism = 4

    override fun extensions(): List<Extension> = listOf(SpringExtension)
}
