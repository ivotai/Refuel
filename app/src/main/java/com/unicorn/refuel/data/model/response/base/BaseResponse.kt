package com.unicorn.refuel.data.model.response.base

import com.unicorn.refuel.app.toast

class BaseResponse<T>(
    val success: Boolean,
    val errorMsg: String,
    val errorCode: String,
    val encryptionData: String,  // data对象json序列化之后进行aes加密的字符串
    val data: T
) {
    val failed: Boolean
        get() {
            val failed = !success
            if (failed) errorMsg.toast()
            return failed
        }
}