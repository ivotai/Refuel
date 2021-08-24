package com.unicorn.refuel.data.model.base

import com.unicorn.refuel.app.defaultPageSize

data class PageRequest<T>(
    val pageNo: Int,        //页码，从1开始
    val pageSize: Int = defaultPageSize,
    val searchParam: T
) :BasePostInfo()