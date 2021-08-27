package com.unicorn.refuel.ui.fra.carFuel

import com.afollestad.materialdialogs.MaterialDialog
import com.rxjava.rxlife.lifeOnMain
import com.unicorn.refuel.app.*
import com.unicorn.refuel.data.event.CarFuelRefreshEvent
import com.unicorn.refuel.data.model.base.EncryptionRequest
import com.unicorn.refuel.data.model.param.carFuel.CarFuelAddParam
import com.unicorn.refuel.ui.fra.carFuel.base.CarFuelDetailFra

class CarFuelAddFra : CarFuelDetailFra() {

    override fun initViews(): Unit = with(binding) {
        super.initViews()
        materialToolbar.title = "添加加油记录"
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
            fuelUpTime = tvFuelUpTime.trimText().toDate().toTransferFormat()
        )
        btnSubmit.isEnabled = false
        api.addCarFuel(EncryptionRequest.create(carFuelAddParam))
            .lifeOnMain(this@CarFuelAddFra)
            .subscribe(
                {
                    btnSubmit.isEnabled = true
                    if (it.failed) return@subscribe
                    "添加记录成功".toast()
                    RxBus.post(CarFuelRefreshEvent())
                    confirm()
                },
                {
                    btnSubmit.isEnabled = true
                    it.errorMsg().toast()
                }
            )
    }

    private fun confirm() {
        MaterialDialog(requireContext()).show {
            title(text = "是否继续添加？")
            positiveButton(text = "继续") { dialog ->
                clear()
            }
            negativeButton(text = "结束") { dialog ->
                finishAct()
            }
        }
    }

    // for add
    private fun clear() = with(binding) {
        tvCarNo.text = ""
        carId = null
        etFuelCardNo.setText("")
        etFuelLabelNumber.setText("")
        etUnitPrice.setText("")
        etFuelAmount.setText("")
        etPrice.setText("")
        etUserName.setText("")
        tvFuelUpTime.text = ""
    }

}