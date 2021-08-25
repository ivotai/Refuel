package com.unicorn.refuel.ui.act

import com.unicorn.refuel.ui.act.base.BaseAct
import com.unicorn.refuel.ui.fra.carFuel.CarFuelAddFra

class CarFuelAddAct:BaseAct() {

    override fun createFra()= CarFuelAddFra()

}