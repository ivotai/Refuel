package com.unicorn.refuel.app

import com.unicorn.refuel.data.model.response.LoggedUser

val uid: String get() = loggedUser.uid

lateinit var loggedUser: LoggedUser

var isLogin = false

var inSelectMode = false