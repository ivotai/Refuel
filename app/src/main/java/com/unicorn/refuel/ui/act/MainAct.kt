package com.unicorn.refuel.ui.act

import com.unicorn.refuel.app.RxBus
import com.unicorn.refuel.app.inSelectMode
import com.unicorn.refuel.data.event.ChangeSelectModeEvent
import com.unicorn.refuel.ui.act.base.BaseAct
import com.unicorn.refuel.ui.fra.MainFra

class MainAct : BaseAct() {

    override fun createFra() = MainFra()
    override fun onBackPressed() {
        if (inSelectMode) {
            RxBus.post(ChangeSelectModeEvent(false))
            return
        }
        super.onBackPressed()
    }
}