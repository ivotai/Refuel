package com.unicorn.refuel.data.model

data class Car(
    val id: Int,
    val name: String,
    val no: String,
    val carType: Int,
    val carTypeDisplay: String,
    val carState: Int,
    val carStateDisplay: String,
    val pictureUrl: String,
    val requisitionUserName: String,
    val orgId: Int
)