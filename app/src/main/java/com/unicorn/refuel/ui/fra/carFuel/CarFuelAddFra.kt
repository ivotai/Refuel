package com.unicorn.refuel.ui.fra.carFuel

import com.rxjava.rxlife.lifeOnMain
import com.unicorn.refuel.app.*
import com.unicorn.refuel.data.event.CarFuelRefreshEvent
import com.unicorn.refuel.data.model.base.EncryptionRequest
import com.unicorn.refuel.data.model.param.carFuel.CarFuelAddParam

class CarFuelAddFra : CarFuelDetailFra() {

    override fun submitX(): Unit = with(binding) {
        val carFuelAddParam = CarFuelAddParam(
            carID = carId!!,
            fuelCardNo = etFuelCardNo.trimText(),
            fuelLabelNumber = etFuelLabelNumber.trimText(),
            unitPrice = etUnitPrice.trimText().toDouble(),
            fuelAmount = etFuelAmount.trimText().toDouble(),
            price = etPrice.trimText().toDouble(),
            userName = etUserName.trimText()
        )
        btnSubmit.isEnabled = false
        api.addCarFuel(EncryptionRequest.create(carFuelAddParam))
            .lifeOnMain(this@CarFuelAddFra)
            .subscribe(
                {
                    btnSubmit.isEnabled = true
                    if (it.failed) return@subscribe
                    "新增记录成功".toast()
                    RxBus.post(CarFuelRefreshEvent())
                    finishAct()
                },
                {
                    btnSubmit.isEnabled = true
                    it.errorMsg().toast()
                }
            )
    }

}