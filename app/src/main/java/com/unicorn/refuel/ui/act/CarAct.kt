package com.unicorn.refuel.ui.act

import com.unicorn.refuel.ui.act.base.BaseAct
import com.unicorn.refuel.ui.fra.CarFra
import com.unicorn.refuel.ui.fra.CarFueFra
import com.unicorn.refuel.ui.fra.LoginFra

class CarAct : BaseAct() {

    override fun createFra() = CarFra()

}