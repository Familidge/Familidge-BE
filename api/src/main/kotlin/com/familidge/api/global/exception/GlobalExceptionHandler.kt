package com.familidge.api.global.exception

import com.familidge.api.global.dto.ErrorResponse
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class GlobalExceptionHandler(
    private val objectMapper: ObjectMapper
) : ErrorWebExceptionHandler {
    override fun handle(exchange: ServerWebExchange, exception: Throwable): Mono<Void> =
        with(exchange.response) {
            val response = ErrorResponse(exception)

            headers.contentType = MediaType.APPLICATION_JSON
            statusCode = HttpStatusCode.valueOf(response.code)

            writeBody(response)
        }

    private fun ServerHttpResponse.writeBody(body: Any) =
        writeWith(
            Mono.just(
                bufferFactory()
                    .wrap(objectMapper.writeValueAsBytes(body))
            )
        )
}
