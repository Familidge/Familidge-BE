package com.familidge.api.global.exception

abstract class ServerException(
    val code: Int,
    override val message: String
) : RuntimeException(message)
