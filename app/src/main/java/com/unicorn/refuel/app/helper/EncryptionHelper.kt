package com.unicorn.refuel.app.helper

import com.blankj.utilcode.util.EncryptUtils
import com.unicorn.refuel.app.loggedUser
import com.unicorn.refuel.app.toast

object EncryptionHelper {

    private const val transformation = "AES/CBC/PKCS7Padding"
    private const val iv = "89DFA17B6BB06A00"

     fun decrypt(data: String): String = with(loggedUser) {
        val result = EncryptUtils.decryptHexStringAES(
            data,
            userKey.toByteArray(),
            transformation,
            iv.toByteArray()
        )
        return String(result)
    }

    fun encrypt(data: String): String = with(loggedUser) {
        val result = EncryptUtils.encryptAES2HexString(
            data.toByteArray(),
            userKey.toByteArray(),
            transformation,
            iv.toByteArray()
        )
        return result
    }

    private fun s() {
        val data = "310115199207010411"
        val key = "15FA3E348C418A86C2781183BCCF580C"
        val iv = "89DFA17B6BB06A00"

        val 加密后 = EncryptUtils.encryptAES2HexString(
            data.toByteArray(),
            key.toByteArray(),
            transformation,
            iv.toByteArray()
        )
        加密后.toast()
        val result2 = EncryptUtils.decryptHexStringAES(
            加密后,
            key.toByteArray(),
            transformation,
            iv.toByteArray()
        )
        val 解密后 = String(result2)
        解密后.toast()
//        val keySpec = SecretKeySpec(key.toByteArray(), "AES")
//        val ivSpec = IvParameterSpec(iv.toByteArray())
//        val cipher = Cipher.getInstance(transformation)
//        cipher.init(Cipher.ENCRYPT_MODE, keySpec,ivSpec)
//        val result = cipher.doFinal(data.toByteArray())
    }

}