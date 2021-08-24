package com.unircorn.csp.data.model.base

import com.unircorn.csp.app.toast

open class Response<T>(
    val message: String?,
    val success: Boolean,
    val data: T
) {
    val failed: Boolean
        get() {
            val failed = !success
            if (failed) message?.toast()
            return failed
        }
}