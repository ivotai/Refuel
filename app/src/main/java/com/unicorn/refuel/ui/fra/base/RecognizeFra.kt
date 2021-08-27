package com.unicorn.refuel.ui.fra.base

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.baidu.ocr.sdk.OCR
import com.baidu.ocr.sdk.OnResultListener
import com.baidu.ocr.sdk.exception.OCRError
import com.baidu.ocr.sdk.model.AccessToken
import com.baidu.ocr.ui.camera.CameraActivity
import com.unicorn.refuel.app.third.FileUtil
import com.unicorn.refuel.app.toast

abstract class RecognizeFra : BaseFra() {

    override fun initBindings() {
        super.initBindings()
        initAccessToken()
    }

    protected lateinit var launcherRecognize: ActivityResultLauncher<Intent>

    protected fun recognize() {
        if (hasGotToken)
            launcherRecognize.launch(getRecognizeIntent())
    }

    private var hasGotToken = false

    private fun initAccessToken() {
        OCR.getInstance(requireContext()).initAccessToken(object : OnResultListener<AccessToken> {
            override fun onResult(result: AccessToken?) {
                hasGotToken = true
            }

            override fun onError(ocrError: OCRError?) {
                "文字识别初始化失败".toast()
            }
        }, requireContext())
    }

    override fun onDestroy() {
        super.onDestroy()
        // 释放内存资源
        OCR.getInstance(requireContext()).release()
    }

    private fun getRecognizeIntent(): Intent {
        val intent = Intent(requireContext(), CameraActivity::class.java)
        intent.putExtra(
            CameraActivity.KEY_OUTPUT_FILE_PATH,
            FileUtil.getSaveFile(requireContext()).absolutePath
        )
        intent.putExtra(
            CameraActivity.KEY_CONTENT_TYPE,
            CameraActivity.CONTENT_TYPE_GENERAL
        )
        return intent
    }

}
