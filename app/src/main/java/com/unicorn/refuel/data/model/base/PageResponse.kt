package com.unicorn.refuel.data.model.base

import com.blankj.utilcode.util.ToastUtils
import com.unicorn.refuel.app.helper.EncryptionHelper
import com.unicorn.refuel.app.toBeanList

class PageResponse<T>(
    val errorCode: String,
    val success: Boolean,
    val errorMsg: String,
    val pageNo: Int,
    val pageSize: Int,
    val total: Int,          // 总数
    val totalPage: Int,      // 总页数
    var items: List<T>,
    val encryptionData: String
) {
    val failed: Boolean
        get() {
            val failed = !success
            if (failed) ToastUtils.showShort(errorMsg)
            return failed
        }

    val itemsJson get() = EncryptionHelper.decrypt(encryptionData)

}