package com.unicorn.refuel.ui.fra.carFuel.base

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.datetime.dateTimePicker
import com.afollestad.materialdialogs.list.listItems
import com.king.zxing.CameraScan
import com.king.zxing.CaptureActivity
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.library.fontawesome.FontAwesome
import com.mikepenz.iconics.utils.sizeDp
import com.unicorn.refuel.app.*
import com.unicorn.refuel.app.third.FileUtil
import com.unicorn.refuel.app.third.RecognizeService
import com.unicorn.refuel.data.event.CarSelectEvent
import com.unicorn.refuel.data.model.Car
import com.unicorn.refuel.data.model.RecognizeResult
import com.unicorn.refuel.databinding.FraCarFuelDetailBinding
import com.unicorn.refuel.ui.act.CarAct
import com.unicorn.refuel.ui.fra.base.RecognizeFra
import java.util.*

abstract class CarFuelDetailFra : RecognizeFra() {

    override fun initViews() = with(binding) {
        btnRecognize.icon = IconicsDrawable(requireContext(), FontAwesome.Icon.faw_image1).apply {
            sizeDp = 24
        }
    }

    @SuppressLint("CheckResult")
    override fun initBindings(): Unit = with(binding) {
        super.initBindings()

        tvCarNo.safeClicks().subscribe {
            MaterialDialog(requireContext()).show {
                title(text = "选择车辆")
                listItems(items = listOf("扫码选择", "列表选择")) { _, index, _ ->
                    if (index == 0) scanCarCode()
                    else startAct(CarAct::class.java)
                }
            }
        }

        tvFuelUpTime.safeClicks().subscribe {
            MaterialDialog(requireContext()).show {
                dateTimePicker { _, dateTime ->
                    tvFuelUpTime.text = dateTime.time.toTransferFormat()
                }
            }
        }

        btnRecognize.safeClicks().subscribe { recognize() }

        btnSubmit.safeClicks().subscribe { submit() }
    }

    private fun submit(): Unit = with(binding) {
        if (tvCarNo.isEmpty() ||
            etFuelCardNo.isEmpty() ||
            etFuelLabelNumber.isEmpty() ||
            etUnitPrice.isEmpty() ||
            etFuelAmount.isEmpty() ||
            etPrice.isEmpty() ||
            etUserName.isEmpty() ||
            tvFuelUpTime.isEmpty()
        ) {
            "请输入所有信息".toast()
            return@with
        }

        try {
            etUnitPrice.trimText().toDouble()
            etFuelAmount.trimText().toDouble()
            etPrice.trimText().toDouble()
        } catch (e: Exception) {
            "数字格式异常，请手动输入".toast()
            return@with
        }

        try {
            tvFuelUpTime.trimText().toDate()
        } catch (e: Exception) {
            "加油时间格式异常，请手动输入".toast()
            return@with
        }

        submitX()
    }

    abstract fun submitX()

    //
    private fun onRecognize(result: String) = with(binding) {
        val recognizeResult = result.toBean<RecognizeResult>()
        recognizeResult.words_result.map { it.words }.forEach {
            if (it.contains("卡号")) {
                etFuelCardNo.setText(it.removePrefix("卡号:"))
            }
            if (it.contains("油品代码")) {
                etFuelLabelNumber.setText(it.removePrefix("油品代码:"))
            }
            if (it.contains("单价")) {
                etUnitPrice.setText(it.removePrefix("单价:").removeSuffix("元/升"))
            }
            if (it.contains("加油升数")) {
                etFuelAmount.setText(it.removePrefix("加油升数:").removeSuffix("升"))
            }
            if (it.contains("应付")) {
                etPrice.setText(it.removePrefix("应付:").removeSuffix("元"))
            }
            if (it.contains("交易时间")) {
                tvFuelUpTime.text = it.removePrefix("交易时间:")
            }
        }
    }

    //

    private fun scanCarCode() {
        launcherScanCarCode.launch(Intent(context, CaptureActivity::class.java))
    }

    protected var carId: Int? = null

    override fun initEvents() = with(binding) {
        RxBus.registerEvent(this@CarFuelDetailFra, CarSelectEvent::class.java, {
            onCar(it.car)
        })
    }

    private fun onCar(car: Car) = with(binding) {
        tvCarNo.text = car.no
        this@CarFuelDetailFra.carId = car.id
    }

    //

    private var _binding: FraCarFuelDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    protected val binding get() = _binding!!

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
                val car = SimpleComponent().gson.fromJson(json, Car::class.java)
                onCar(car)
            }

        launcherRecognize =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.data == null) return@registerForActivityResult
                RecognizeService.recognizeAccurateBasic(
                    requireContext(),
                    FileUtil.getSaveFile(requireContext()).absolutePath
                ) { result -> onRecognize(result = result) }
            }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}