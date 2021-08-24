package com.unicorn.refuel.ui.fra

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.baidu.ocr.sdk.OCR
import com.baidu.ocr.sdk.OnResultListener
import com.baidu.ocr.sdk.exception.OCRError
import com.baidu.ocr.sdk.model.AccessToken
import com.baidu.ocr.ui.camera.CameraActivity
import com.unicorn.refuel.app.safeClicks
import com.unicorn.refuel.app.third.FileUtil
import com.unicorn.refuel.app.third.RecognizeService
import com.unicorn.refuel.app.toast
import com.unicorn.refuel.databinding.FraCarFuelDetailBinding
import com.unicorn.refuel.ui.fra.base.BaseFra

class CarFuelAddFra : BaseFra() {

    override fun initBindings() {
        super.initBindings()
        initAccessToken()

        binding.materialToolbar.safeClicks().subscribe {
            recognize()
        }
    }

    private var hasGotToken = false

    private fun initAccessToken() {
        OCR.getInstance(requireContext()).initAccessToken(object : OnResultListener<AccessToken> {
            override fun onResult(result: AccessToken?) {
                val token = result?.accessToken
                "文字识别初始化成功".toast()
                hasGotToken = true

            }

            override fun onError(ocrError: OCRError?) {
                ocrError?.message.toast()
            }
        }, requireContext())
    }

    private fun recognize() {
        if (!checkTokenStatus()) {
            return
        }
        val intent = Intent(requireContext(), CameraActivity::class.java)
        intent.putExtra(
            CameraActivity.KEY_OUTPUT_FILE_PATH,
            FileUtil.getSaveFile(requireContext()).absolutePath
        )
        intent.putExtra(
            CameraActivity.KEY_CONTENT_TYPE,
            CameraActivity.CONTENT_TYPE_GENERAL
        )
        startActivityForResult(intent, 2333)

    }

    private fun checkTokenStatus(): Boolean {
        if (!hasGotToken) {
            Toast.makeText(requireContext(), "token还未成功获取", Toast.LENGTH_LONG).show()
        }
        return hasGotToken
    }


    override fun onDestroy() {
        super.onDestroy()
        // 释放内存资源
        OCR.getInstance(requireContext()).release()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2333 && resultCode == Activity.RESULT_OK) {
            RecognizeService.recognizeGeneralBasic(requireContext(),
                FileUtil.getSaveFile(requireContext()).absolutePath
            ) { result -> result?.toast() }

        }
    }


    // 识别成功回调，通用文字识别


    //

    private var _binding: FraCarFuelDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FraCarFuelDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}