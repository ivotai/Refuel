package com.unicorn.refuel.data.model.base

import com.unicorn.refuel.app.SimpleComponent
import com.unicorn.refuel.app.helper.EncryptionHelper
import com.unicorn.refuel.app.loggedUser


data class EncryptionRequest(
    val Param: String,
    val UserToken: String = loggedUser.userToken
) {
    companion object {
        fun create(any: Any): EncryptionRequest {
            // 确保从服务获取的数据也被填充 uid
//            if (basePostInfo.idCardNumber == null) basePostInfo.idCardNumber = loggedUser.uid
            val json = SimpleComponent().gson.toJson(any)
            val param = EncryptionHelper.encrypt(json)
            return EncryptionRequest(Param = param)
        }

//        fun createForBasePostInfo(): GeneralParam {
//            return create(basePostInfo = BasePostInfo())
//        }

//        fun createForOrgParam(): GeneralParam {
//            return create(basePostInfo = OrgParam())
//        }
    }
}