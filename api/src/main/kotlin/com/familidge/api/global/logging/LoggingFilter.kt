package com.familidge.api.global.logging

import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.oshai.kotlinlogging.withLoggingContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.slf4j.MDCContext
import kotlinx.coroutines.withContext
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.core.io.buffer.DataBufferUtils
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.stereotype.Component
import org.springframework.web.server.CoWebFilter
import org.springframework.web.server.CoWebFilterChain
import org.springframework.web.server.ServerWebExchange
import java.util.*
import kotlin.reflect.jvm.jvmName

@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
class LoggingFilter : CoWebFilter() {
    private val logger = KotlinLogging.logger(this::class.jvmName)

    override suspend fun filter(exchange: ServerWebExchange, chain: CoWebFilterChain) {
        withTraceId {
            with(exchange.request) {
                val query = uri.query?.let { "?$it" } ?: ""
                val body = if (isJson()) awaitBody() else ""

                logger.info { "HTTP $method $path$query $body" }
            }
            chain.filter(exchange)
            logger.info { "HTTP ${exchange.response.statusCode}" }
        }
    }

    private suspend fun <T> withTraceId(block: suspend CoroutineScope.() -> T): T =
        withLoggingContext("traceId" to UUID.randomUUID().toString().take(8)) {
            withContext(MDCContext(), block)
        }

    private suspend fun ServerHttpRequest.awaitBody(): String {
        val buffer = DataBufferUtils.join(body.cache())
            .awaitSingle()
        val bytes = ByteArray(buffer.readableByteCount())
            .also(buffer::read)

        DataBufferUtils.release(buffer)

        return bytes.toString(Charsets.UTF_8)
            .toPrettyJson()
    }

    private fun String.toPrettyJson() =
        replace(Regex("\\n"), "")
            .replace(Regex("\\s+"), "")
            .replace(Regex("(,)(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"), ", ")

    private fun ServerHttpRequest.isJson(): Boolean =
        method in listOf(HttpMethod.POST, HttpMethod.PUT) && headers.contentType == MediaType.APPLICATION_JSON
}
