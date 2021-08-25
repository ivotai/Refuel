package com.unicorn.refuel.data.model.param.carFuel

import com.unicorn.refuel.app.toTransferFormat
import com.unicorn.refuel.data.model.base.BasePostInfo
import java.util.*

data class CarFuelUpdateParam(
    val id: Int,
    val carID: Int,
    // todo 加油时间怎么更新
    val fuelUpTime: String = Date().toTransferFormat(),
    val fuelCardNo: String,
    val fuelLabelNumber: String,
    val unitPrice: Double,
    val fuelAmount: Double,
    val price: Double,
    val userName: String
) : BasePostInfo()