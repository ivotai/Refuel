package com.unicorn.refuel.ui.fra.carFuel

import com.rxjava.rxlife.lifeOnMain
import com.unicorn.refuel.app.*
import com.unicorn.refuel.data.event.CarFuelRefreshEvent
import com.unicorn.refuel.data.model.base.EncryptionRequest
import com.unicorn.refuel.data.model.param.carFuel.CarFuelAddParam
import com.unicorn.refuel.ui.fra.carFuel.base.CarFuelDetailFra

class CarFuelAddFra : CarFuelDetailFra() {

    override fun initViews():Unit = with(binding) {
        super.initViews()
        materialToolbar.title = "添加车辆加油记录"
    }

    override fun submitX(): Unit = with(binding) {
        val carFuelAddParam = CarFuelAddParam(
            carID = carId!!,
            fuelCardNo = etFuelCardNo.trimText(),
            fuelLabelNumber = etFuelLabelNumber.trimText(),
            unitPrice = etUnitPrice.trimText().toDouble(),
            fuelAmount = etFuelAmount.trimText().toDouble(),
            price = etPrice.trimText().toDouble(),
            userName = etUserName.trimText(),
            // todo
            fuelUpTime = tvFuelUpTime.trimText()
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