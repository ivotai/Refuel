package com.unicorn.refuel.ui.fra

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blankj.utilcode.util.EncryptUtils
import com.blankj.utilcode.util.ToastUtils
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.library.fontawesome.FontAwesome
import com.mikepenz.iconics.utils.sizeDp
import com.rxjava.rxlife.lifeOnMain
import com.unicorn.refuel.app.*
import com.unicorn.refuel.data.model.param.UserLoginParam
import com.unicorn.refuel.databinding.FraLoginBinding
import com.unicorn.refuel.ui.fra.base.BaseFra

class LoginFra : BaseFra() {

    override fun initViews() = with(binding) {
        tilUsername.startIconDrawable =
            IconicsDrawable(requireContext(), FontAwesome.Icon.faw_user1).apply {
                sizeDp = 24
            }
        tilPassword.startIconDrawable =
            IconicsDrawable(requireContext(), FontAwesome.Icon.faw_lock).apply {
                sizeDp = 24
            }
        with(UserInfo) {
            etLoginStr.setText(loginStr)
            etUserPwd.setText(userPwd)
        }
    }


    override fun initBindings(): Unit = with(binding) {
        btnLogin.safeClicks().subscribe { loginX() }
    }


    private fun loginX() = with(binding) {
        if (etLoginStr.isEmpty()) {
            ("手机号码或登录名称不能为空").toast()
            return@with
        }
        if (etUserPwd.isEmpty()) {
            ("密码不能为空").toast()
            return@with
        }
        login()
    }

    private fun login() = with(binding) {
        val userLoginParam = UserLoginParam(
            loginStr = etLoginStr.trimText(),
            userPwd = EncryptUtils.encryptMD5ToString(etUserPwd.trimText())
        )
        api.login(userLoginParam = userLoginParam)
            .lifeOnMain(this@LoginFra)
            .subscribe(
                { response ->
                    if (response.failed) return@subscribe
//                    loggedUser = response.data
//                    isLogin = true
//                    saveUserInfo()
//                    toActAndFinish(MainAct::class.java)
                },
                {
                    it.errorMsg().toast()
                }
            )


//        api.login(etUsername.trimText(), etPassword.trimText())
//            .lifeOnMain(this@LoginFra)
//            .subscribe(
//                {
//                    if (it.failed) return@subscribe
//                    Globals.loginResponse = it
//                    Globals.isLogout = false
//                    with(UserInfo) {
//                        username = etUsername.trimText()
//                        password = etPassword.trimText()
//                    }
//                    VersionHelper.checkVersion(requireActivity())
//                },
//                { it.errorMsg().toast() }
//            )
    }

    // ----

    private var _binding: FraLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FraLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}