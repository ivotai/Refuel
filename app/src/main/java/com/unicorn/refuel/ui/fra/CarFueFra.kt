package com.unicorn.refuel.ui.fra

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.blankj.utilcode.util.ConvertUtils
import com.king.zxing.CameraScan
import com.king.zxing.CaptureActivity
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum
import com.nightonke.boommenu.BoomButtons.HamButton
import com.nightonke.boommenu.ButtonEnum
import com.nightonke.boommenu.Piece.PiecePlaceEnum
import com.unicorn.refuel.R
import com.unicorn.refuel.app.*
import com.unicorn.refuel.data.event.CarFueSearchEvent
import com.unicorn.refuel.data.model.CarFuel
import com.unicorn.refuel.data.model.base.EncryptionRequest
import com.unicorn.refuel.data.model.base.PageRequest
import com.unicorn.refuel.data.model.base.PageResponse
import com.unicorn.refuel.data.model.param.CarFuelListParam
import com.unicorn.refuel.databinding.FraCarFuelBinding
import com.unicorn.refuel.ui.adapter.CarFuelAdapter
import com.unicorn.refuel.ui.fra.base.PageFra
import io.reactivex.rxjava3.core.Single


class CarFueFra : PageFra<CarFuel>() {

    override fun initViews() = with(binding) {
        super.initViews()
        initBoomMenuButton()
    }

    @SuppressLint("CheckResult")
    private fun initBoomMenuButton() {
        with(binding.boomMenuButton) {
            buttonEnum = ButtonEnum.Ham
            normalColor = getAttrColor(R.attr.colorPrimary)
            highlightedColor = getAttrColor(R.attr.colorPrimaryVariant)
            piecePlaceEnum = PiecePlaceEnum.HAM_3
            buttonPlaceEnum = ButtonPlaceEnum.HAM_3
            isInFragment = true
        }
        with(binding.boomMenuButton) {
            HamButton.Builder()
                .normalTextColor(requireContext().getColorFromAttr(R.attr.colorPrimary))
                .normalColor(android.graphics.Color.WHITE)
                .highlightedColor(getAttrColor(R.attr.colorPrimary))
                .subNormalTextColor(requireContext().getColorFromAttr(R.attr.colorPrimary))
                .normalText("新增车辆加油记录")
                .subNormalText("创建一条新的车辆加油记录")
                .listener {
                    "T".toast()
                }
                .shadowRadius(ConvertUtils.dp2px(1f))
                .let { addBuilder(it) }

            HamButton.Builder()
                .normalTextColor(requireContext().getColorFromAttr(com.unicorn.refuel.R.attr.colorPrimary))
                .normalColor(android.graphics.Color.WHITE)
                .subNormalTextColor(requireContext().getColorFromAttr(com.unicorn.refuel.R.attr.colorPrimary))
                .normalText("查询车辆加油记录")
                .subNormalText("输入车牌号查询车辆加油记录，支持模糊查询")
                .listener {
                    MaterialDialog(requireContext()).show {
                        input(allowEmpty = true, hint = "输入车牌号") { _, text ->
                            RxBus.post(CarFueSearchEvent(carNo = text.toString()))
                        }
                        positiveButton(text = "确认")
                    }
                }
                .shadowRadius(ConvertUtils.dp2px(1f))
                .let { addBuilder(it) }

            HamButton.Builder()
                .normalTextColor(requireContext().getColorFromAttr(com.unicorn.refuel.R.attr.colorPrimary))
                .normalColor(android.graphics.Color.WHITE)
                .subNormalTextColor(requireContext().getColorFromAttr(com.unicorn.refuel.R.attr.colorPrimary))
                .normalText("分享车辆加油记录")
                .subNormalText("将车辆加油记录导出成 Excel 并分享")
                .listener {
                    // todo
                    "分享功能还未实现".toast()
                }
                .shadowRadius(ConvertUtils.dp2px(1f))
                .let { addBuilder(it) }
        }
    }

    @SuppressLint("CheckResult")
    override fun initBindings(): Unit = with(binding) {
        super.initBindings()

//            startAct(CarFuelAddAct::class.java)
//            MaterialDialog(requireContext()).show {
//                title(text = "选择加油车辆")
//                listItems(items = listOf("车辆扫码", "车辆列表选择")) { _, index, _ ->
//                    if (index == 0) {
//                        scanCode()
//                    } else {
//                        startAct(CarAct::class.java)
//                    }
//                }
//            }

    }

    override fun initEvents() {
        super.initEvents()
        RxBus.registerEvent(this, CarFueSearchEvent::class.java, {
            this.carNo = it.carNo
            loadStartPage()
        })
    }

    private fun scanCode() {
        activityResultLauncher.launch(Intent(context, CaptureActivity::class.java))
    }

    override fun addItemDecoration() {
        // no item decoration
    }

    override fun initPageAdapter() {
        pageAdapter = CarFuelAdapter()
    }

    private var carNo = ""

    override fun loadPage(page: Int): Single<PageResponse<CarFuel>> =
        api.getCarFuelList(
            EncryptionRequest.create(
                PageRequest(
                    pageNo = page,
                    searchParam = CarFuelListParam(carNo = carNo)
                )
            )
        ).doOnSuccess { it.items = it.itemsJson.toBeanList() }

    override val mRecyclerView: RecyclerView
        get() = binding.recyclerView
    override val mSwipeRefreshLayout: SwipeRefreshLayout
        get() = binding.swipeRefreshLayout

    //

    private var _binding: FraCarFuelBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FraCarFuelBinding.inflate(inflater, container, false)

        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                val carId = CameraScan.parseScanResult(it.data)
                carId.toast()
            }

        return binding.root
    }

    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}