package com.unicorn.refuel.app

import com.chibatching.kotpref.KotprefModel

object UserInfo : KotprefModel() {
    var loginStr by stringPref()    // 手机号码或登录名称
    var userPwd by stringPref()     // 密码，md5加密
}