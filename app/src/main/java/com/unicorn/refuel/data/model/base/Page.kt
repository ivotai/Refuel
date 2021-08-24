package com.unircorn.csp.data.model.base

data class Page<T>(
    val content: List<T>,
    val totalElements: String
)

