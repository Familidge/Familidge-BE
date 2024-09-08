package com.familidge.api.global.logging

import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.oshai.kotlinlogging.withLoggingContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.mono
import kotlinx.coroutines.slf4j.MDCContext
import kotlinx.coroutines.withContext
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.core.io.buffer.DataBufferUtils
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpRequestDecorator
import org.springframework.stereotype.Component
import org.springframework.web.server.CoWebFilter
import org.springframework.web.server.CoWebFilterChain
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*
import kotlin.reflect.jvm.jvmName

@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
class LoggingFilter : CoWebFilter() {
    private val logger = KotlinLogging.logger(this::class.jvmName)

    override suspend fun filter(exchange: ServerWebExchange, chain: CoWebFilterChain) {
        withTraceId {
            val request =
                with(exchange.request) {
                    val bytes =
                        exchange.request
                            .run {
                                body.takeIf { isJson() }
                                    ?.run {
                                        toByteArray()
                                            .awaitSingle()
                                    }
                            }
                    val query = uri.query?.let { "?$it" } ?: ""

                    logger.info { "HTTP $method $path$query ${bytes?.toString(Charsets.UTF_8)?.toPrettyJson() ?: ""}" }

                    object : ServerHttpRequestDecorator(exchange.request) {
                        override fun getBody(): Flux<DataBuffer> =
                            if (bytes == null)
                                exchange.request.body
                            else
                                Flux.just(
                                    exchange.response
                                        .bufferFactory()
                                        .wrap(bytes)
                                )
                    }
                }

            with(exchange.response) {
                beforeCommit {
                    mono(coroutineContext.minusKey(Job)) {
                        logger.info { "HTTP $statusCode" }
                        null
                    }
                }
            }

            chain.filter(
                exchange.mutate()
                    .request(request)
                    .build()
            )
        }
    }

    private suspend fun <T> withTraceId(block: suspend CoroutineScope.() -> T): T =
        withLoggingContext("traceId" to UUID.randomUUID().toString().take(8)) {
            withContext(MDCContext(), block)
        }

    private fun ServerHttpRequest.isJson(): Boolean =
        method in listOf(HttpMethod.POST, HttpMethod.PUT) && headers.contentType == MediaType.APPLICATION_JSON

    private fun Flux<DataBuffer>.toByteArray(): Mono<ByteArray> =
        DataBufferUtils.join(this)
            .map { buffer ->
                ByteArray(buffer.readableByteCount())
                    .also {
                        buffer.read(it)
                        DataBufferUtils.release(buffer)
                    }
            }

    private fun String.toPrettyJson() =
        replace(Regex("\\n"), "")
            .replace(Regex("\\s+"), "")
            .replace(Regex("(,)(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"), ", ")
}
