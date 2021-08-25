package com.unicorn.refuel.data.model

import com.unicorn.refuel.data.model.base.BasePostInfo
import java.io.Serializable
import java.util.*

data class CarFuel(
    val id: Int,
    val carID: Int,
    val carNo: String,
    val fuelUpTime: Date,
    val fuelCardNo: String,
    val fuelLabelNumber: String,
    val unitPrice: Double,
    val fuelAmount: Double,
    val price: Double,
    val userName: String,
    val createdBy: String,
    val createdByUserName: String,
    val createdServerTime: Date
) : BasePostInfo(), Serializable