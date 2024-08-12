import com.epages.restdocs.apispec.gradle.OpenApi3Task

dependencies {
    implementation(project(":infrastructure"))
    implementation(project(":application"))
    implementation(project(":domain"))
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    testFixturesImplementation("org.springframework.boot:spring-boot-starter-test")
    testFixturesImplementation(libs.bundles.kotest)
    testFixturesImplementation(libs.bundles.mockk)
    testFixturesImplementation(libs.bundles.restdocs)
}

tasks {
    test {
        finalizedBy(withType<OpenApi3Task>())
    }

    jar {
        enabled = false
    }

    bootJar {
        enabled = true
    }

    openapi3 {
        title = "Familidge API"
        version = "v1"
        format = "yaml"
        outputFileNamePrefix = "api"
        outputDirectory = "src/main/resources/static/docs"
    }
}
