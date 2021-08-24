package com.unicorn.refuel.data.model.response

data class LoggedUser(
    val encryptionId: String,
    val orgID: Int,
    val orgName: String,
    val role: Int,
    val roleName: String,
    val sid: Any,
    val uid: String,
    val userKey: String,
    val userName: String,
    val userToken: String
)