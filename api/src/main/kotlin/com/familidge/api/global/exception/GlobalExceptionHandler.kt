package com.familidge.api.global.exception

import com.familidge.api.global.dto.ErrorResponse
import com.fasterxml.jackson.databind.ObjectMapper
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import kotlin.reflect.jvm.jvmName

@Component
class GlobalExceptionHandler(
    private val objectMapper: ObjectMapper
) : ErrorWebExceptionHandler {
    private val logger = KotlinLogging.logger(this::class.jvmName)

    override fun handle(exchange: ServerWebExchange, exception: Throwable): Mono<Void> =
        with(exchange.response) {
            val body = ErrorResponse(exception)

            logger.error { "${exception::class.simpleName}(\"${exception.message ?: ""}\") at ${exception.stackTrace[0]}" }

            headers.contentType = MediaType.APPLICATION_JSON
            statusCode = HttpStatusCode.valueOf(body.code)

            writeBody(body)
        }

    private fun ServerHttpResponse.writeBody(body: Any): Mono<Void> =
        writeWith(
            Mono.just(
                bufferFactory()
                    .wrap(objectMapper.writeValueAsBytes(body))
            )
        )
}
