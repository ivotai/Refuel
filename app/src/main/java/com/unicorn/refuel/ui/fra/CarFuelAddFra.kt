package com.unicorn.refuel.ui.fra

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems
import com.baidu.ocr.sdk.OCR
import com.baidu.ocr.sdk.OnResultListener
import com.baidu.ocr.sdk.exception.OCRError
import com.baidu.ocr.sdk.model.AccessToken
import com.baidu.ocr.ui.camera.CameraActivity
import com.king.zxing.CameraScan
import com.king.zxing.CaptureActivity
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.library.fontawesome.FontAwesome
import com.mikepenz.iconics.utils.sizeDp
import com.unicorn.refuel.app.*
import com.unicorn.refuel.app.third.FileUtil
import com.unicorn.refuel.app.third.RecognizeService
import com.unicorn.refuel.data.event.CarSelectEvent
import com.unicorn.refuel.databinding.FraCarFuelDetailBinding
import com.unicorn.refuel.ui.act.CarAct
import com.unicorn.refuel.ui.fra.base.BaseFra

class CarFuelAddFra : BaseFra() {

    override fun initViews() = with(binding) {
        binding.btnScan.icon =
            IconicsDrawable(requireContext(), FontAwesome.Icon.faw_image1).apply {
                sizeDp = 24
            }
    }

    @SuppressLint("CheckResult")
    override fun initBindings(): Unit = with(binding) {
        super.initBindings()
        initAccessToken()

        tvCarNo.safeClicks().subscribe {
            MaterialDialog(requireContext()).show {
                listItems(items = listOf("扫码选择", "列表选择")) { _, index, _ ->
                    if (index == 0) scanCarCode()
                    else startAct(CarAct::class.java)
                }
            }
        }

    }

    private fun scanCarCode() {
        launcherScanCarCode.launch(Intent(context, CaptureActivity::class.java))
    }

    private var carId: Int? = null

    override fun initEvents() = with(binding) {
        RxBus.registerEvent(this@CarFuelAddFra, CarSelectEvent::class.java, {
            tvCarNo.text = it.car.no
            this@CarFuelAddFra.carId = it.car.id
        })
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

    //

    private fun checkTokenStatus(): Boolean {
        if (!hasGotToken) {
            Toast.makeText(requireContext(), "token还未成功获取", Toast.LENGTH_LONG).show()
        }
        return hasGotToken
    }

    private var hasGotToken = false

    private fun initAccessToken() {
        OCR.getInstance(requireContext()).initAccessToken(object : OnResultListener<AccessToken> {
            override fun onResult(result: AccessToken?) {
                "文字识别初始化成功".toast()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2333 && resultCode == Activity.RESULT_OK) {
            RecognizeService.recognizeGeneralBasic(
                requireContext(),
                FileUtil.getSaveFile(requireContext()).absolutePath
            ) { result -> result?.toast() }

        }
    }

    //

    private var _binding: FraCarFuelDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    lateinit var launcherScanCarCode: ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FraCarFuelDetailBinding.inflate(inflater, container, false)

        launcherScanCarCode =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.data == null) return@registerForActivityResult
                val json = CameraScan.parseScanResult(it.data)
                // todo
                json.toast()
            }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}