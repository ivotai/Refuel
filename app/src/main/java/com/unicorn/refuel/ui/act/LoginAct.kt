package com.unicorn.refuel.ui.act

import com.unicorn.refuel.ui.act.base.BaseAct
import com.unicorn.refuel.ui.fra.LoginFra

class LoginAct : BaseAct() {

    override fun createFragment() = LoginFra()

}