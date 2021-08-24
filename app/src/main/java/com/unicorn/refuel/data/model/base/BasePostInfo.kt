package com.unicorn.refuel.data.model.base

import com.unicorn.refuel.app.loggedUser

open class BasePostInfo(val idCardNumber: String = loggedUser.uid)