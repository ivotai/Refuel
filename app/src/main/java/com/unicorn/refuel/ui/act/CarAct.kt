package com.unicorn.refuel.ui.act

import com.unicorn.refuel.ui.act.base.BaseAct
import com.unicorn.refuel.ui.fra.CarFra

class CarAct : BaseAct() {

    override fun createFra() = CarFra()

}