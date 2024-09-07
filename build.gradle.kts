plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
    alias(libs.plugins.spring.kotlin)
    alias(libs.plugins.java.library)
    alias(libs.plugins.java.fixture)
    alias(libs.plugins.restdocs.openapi)
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
        plugin(rootProject.libs.plugins.java.library)
        plugin(rootProject.libs.plugins.java.fixture)
        plugin(rootProject.libs.plugins.restdocs.openapi)
    }

    configurations {
        all {
            exclude("org.springframework.boot", "spring-boot-starter-logging")
        }
    }

    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
        implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
        implementation(rootProject.libs.bundles.logging)
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("org.springframework.security:spring-security-test")
        testImplementation(rootProject.libs.bundles.kotest)
        testImplementation(rootProject.libs.bundles.mockk)
        testImplementation(rootProject.libs.bundles.restdocs)
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
