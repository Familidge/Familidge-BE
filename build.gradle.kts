plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
    alias(libs.plugins.spring.kotlin)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

allprojects {
    group = "com.familidge"
    version = "1.0.0"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply {
        plugin(rootProject.libs.plugins.kotlin)
        plugin(rootProject.libs.plugins.spring.boot)
        plugin(rootProject.libs.plugins.spring.dependency.management)
        plugin(rootProject.libs.plugins.spring.kotlin)
    }

    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("org.springframework.security:spring-security-test")
        testImplementation(rootProject.libs.bundles.kotest)
        testImplementation(rootProject.libs.bundles.mockk)
    }

    tasks {
        compileKotlin {
            kotlinOptions {
                freeCompilerArgs += "-Xjsr305=strict"
                jvmTarget = "17"
            }
        }

        jar {
            enabled = true
        }

        bootJar {
            enabled = false
        }

        test {
            useJUnitPlatform()
        }
    }
}

private fun ObjectConfigurationAction.plugin(provider: Provider<PluginDependency>): ObjectConfigurationAction =
    plugin(provider.get().pluginId)
