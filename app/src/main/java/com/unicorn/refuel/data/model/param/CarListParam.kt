package com.unicorn.refuel.data.model.param

import com.unicorn.refuel.app.loggedUser

data class CarListParam(
    val carType: Int? = null,
    val carState: Int? = null,
    val name: String = "",
    val orgId: Int = loggedUser.orgID
)