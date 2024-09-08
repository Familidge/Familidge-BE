package com.familidge.api.common

import com.familidge.api.config.ApplicationConfiguration
import com.familidge.api.config.SecurityTestConfiguration
import com.familidge.api.global.exception.GlobalExceptionHandler
import io.kotest.core.spec.style.DescribeSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Import
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.WebTestClient

@ContextConfiguration(classes = [ApplicationConfiguration::class, SecurityTestConfiguration::class])
@Import(GlobalExceptionHandler::class)
@WebFluxTest
@AutoConfigureRestDocs
abstract class ControllerTest : DescribeSpec() {
    @Autowired
    private lateinit var applicationContext: ApplicationContext

    @Autowired
    private lateinit var restDocumentation: RestDocumentationContextProvider

    protected val webClient: WebTestClient by lazy {
        WebTestClient.bindToApplicationContext(applicationContext)
            .configureClient()
            .filter(WebTestClientRestDocumentation.documentationConfiguration(restDocumentation))
            .build()
    }
}
