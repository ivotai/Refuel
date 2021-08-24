package com.unicorn.refuel.data.model.response

import com.unicorn.refuel.app.helper.EncryptionHelper

data class LoggedUser(
    val encryptionId: String,
    val orgID: Int,
    val orgName: String,
    val role: Int,
    val roleName: String,
    val sid: Any,
    val userKey: String,
    val userName: String,
    val userToken: String
) {
    val uid: String get() = EncryptionHelper.decrypt(encryptionId)
}