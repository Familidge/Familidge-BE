package com.familidge.infrastructure.global.common

interface Entity<T> {
    fun toDomain(): T
}
