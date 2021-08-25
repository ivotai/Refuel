package com.unicorn.refuel.data.model.param.carFuel

import com.unicorn.refuel.data.model.base.BasePostInfo

data class CarFuelUpdateParam(
    val id: Int,
    val carID: Int,
    val fuelUpTime: String,
    val fuelCardNo: String,
    val fuelLabelNumber: String,
    val unitPrice: Double,
    val fuelAmount: Double,
    val price: Double,
    val userName: String
) : BasePostInfo()