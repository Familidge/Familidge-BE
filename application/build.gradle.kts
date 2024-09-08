dependencies {
    implementation(project(":domain"))
    implementation("org.springframework:spring-context")
    implementation(libs.bundles.jwt)
    testImplementation(testFixtures(project(":domain")))
    testFixturesImplementation(testFixtures(project(":domain")))
}
