package com.unicorn.refuel.ui.fra.carFuel

import com.rxjava.rxlife.lifeOnMain
import com.unicorn.refuel.app.*
import com.unicorn.refuel.data.event.CarFuelRefreshEvent
import com.unicorn.refuel.data.model.CarFuel
import com.unicorn.refuel.data.model.base.EncryptionRequest
import com.unicorn.refuel.data.model.param.carFuel.CarFuelUpdateParam
import com.unicorn.refuel.ui.fra.carFuel.base.CarFuelDetailFra

class CarFuelUpdateFra : CarFuelDetailFra() {

    private val carFuel by lazy { requireArguments().getSerializable(Param) as CarFuel }

    override fun initViews() = with(binding) {
        super.initViews()
        materialToolbar.title = "修改加油记录"
        with(carFuel) {
            tvCarNo.text = carNo
            carId = carID
            etFuelCardNo.setText(fuelCardNo)
            etFuelLabelNumber.setText(fuelLabelNumber)
            etUnitPrice.setText(unitPrice.toString())
            etFuelAmount.setText(fuelAmount.toString())
            etPrice.setText(price.toString())
            etUserName.setText(userName)
            tvFuelUpTime.text = fuelUpTime.toDisplayFormat()
        }
    }

    override fun submitX(): Unit = with(binding) {
        val carFuelUpdateParam = CarFuelUpdateParam(
            id = carFuel.id,
            carID = carId!!,
            fuelCardNo = etFuelCardNo.trimText(),
            fuelLabelNumber = etFuelLabelNumber.trimText(),
            unitPrice = etUnitPrice.trimText().toDouble(),
            fuelAmount = etFuelAmount.trimText().toDouble(),
            price = etPrice.trimText().toDouble(),
            userName = etUserName.trimText(),
            fuelUpTime = tvFuelUpTime.trimText()
        )
        btnSubmit.isEnabled = false
        api.updateCarFuel(EncryptionRequest.create(carFuelUpdateParam))
            .lifeOnMain(this@CarFuelUpdateFra)
            .subscribe(
                {
                    btnSubmit.isEnabled = true
                    if (it.failed) return@subscribe
                    "修改记录成功".toast()
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