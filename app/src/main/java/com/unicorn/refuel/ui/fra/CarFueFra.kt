package com.unicorn.refuel.ui.fra

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.library.fontawesome.FontAwesome
import com.unicorn.refuel.app.safeClicks
import com.unicorn.refuel.app.startAct
import com.unicorn.refuel.app.toBeanList
import com.unicorn.refuel.data.model.CarFuel
import com.unicorn.refuel.data.model.base.EncryptionRequest
import com.unicorn.refuel.data.model.base.PageRequest
import com.unicorn.refuel.data.model.base.PageResponse
import com.unicorn.refuel.data.model.param.CarFuelListParam
import com.unicorn.refuel.databinding.FraCarFuelBinding
import com.unicorn.refuel.ui.act.CarAct
import com.unicorn.refuel.ui.adapter.CarFuelAdapter
import com.unicorn.refuel.ui.fra.base.PageFra
import com.unicorn.refuel.ui.pager.MainPagerAdapter
import io.reactivex.rxjava3.core.Single

class CarFueFra : PageFra<CarFuel>() {

    override fun initViews() = with(binding) {
        super.initViews()
        with(extendedFloatingActionButton) {
            icon = IconicsDrawable(requireContext(), FontAwesome.Icon.faw_plus)
            text = MainPagerAdapter.titles[0]
        }
    }

    @SuppressLint("CheckResult")
    override fun initBindings(): Unit = with(binding) {
        super.initBindings()
        extendedFloatingActionButton.safeClicks().subscribe {
            MaterialDialog(requireContext()).show {
                title(text = "选择加油车辆")
                listItems(items = listOf("车辆扫码", "车辆列表选择")) { _, index, _ ->
                    if (index == 0) {
                        // todo 扫码
                    } else {
                        startAct(CarAct::class.java)
                    }
                }
            }
        }
    }

    override fun addItemDecoration() {
        // no item decoration
    }

    override fun initPageAdapter() {
        pageAdapter = CarFuelAdapter()
    }

    override fun loadPage(page: Int): Single<PageResponse<CarFuel>> =
        api.getCarFuelList(
            EncryptionRequest.create(
                PageRequest(
                    pageNo = page,
                    searchParam = CarFuelListParam()
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
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}