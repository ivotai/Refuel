package com.unicorn.refuel.data.model.param

import com.unicorn.refuel.app.loggedUser
import com.unicorn.refuel.data.model.base.BasePostInfo

data class CarFuelListParam(
    val carNo: String = ""
):BasePostInfo()