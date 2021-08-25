package com.unicorn.refuel.data.model.param

import com.unicorn.refuel.app.toTransferFormat
import com.unicorn.refuel.data.model.base.BasePostInfo
import java.util.*

data class CarFuelAddParam(
    val carID: Int,
    val fuelUpTime: String = Date().toTransferFormat(),
    val fuelCardNo: String,
    val fuelLabelNumber: String,
    val unitPrice: Double,
    val fuelAmount: Double,
    val price: Double,
    val userName: String
) : BasePostInfo()