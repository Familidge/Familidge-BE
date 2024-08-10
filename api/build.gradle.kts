dependencies {
    implementation(project(":infrastructure"))
    implementation(project(":application"))
    implementation(project(":domain"))
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-security")
}

tasks {
    jar {
        enabled = false
    }

    bootJar {
        enabled = true
    }
}
