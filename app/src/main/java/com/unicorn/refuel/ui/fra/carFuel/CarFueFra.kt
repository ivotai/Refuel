package com.unicorn.refuel.ui.fra.carFuel

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.blankj.utilcode.util.ToastUtils
import com.unicorn.refuel.R
import com.unicorn.refuel.app.RxBus
import com.unicorn.refuel.app.helper.ExportHelper
import com.unicorn.refuel.app.inSelectMode
import com.unicorn.refuel.app.startAct
import com.unicorn.refuel.app.toBeanList
import com.unicorn.refuel.data.event.CarFuelRefreshEvent
import com.unicorn.refuel.data.event.CarFuelSearchEvent
import com.unicorn.refuel.data.event.ChangeSelectModeEvent
import com.unicorn.refuel.data.model.CalFuelSelect
import com.unicorn.refuel.data.model.base.EncryptionRequest
import com.unicorn.refuel.data.model.base.PageRequest
import com.unicorn.refuel.data.model.base.PageResponse
import com.unicorn.refuel.data.model.param.carFuel.CarFuelListParam
import com.unicorn.refuel.databinding.UiTitleSwipeBinding
import com.unicorn.refuel.ui.act.CarFuelAddAct
import com.unicorn.refuel.ui.adapter.CarFuelAdapter
import com.unicorn.refuel.ui.fra.base.PageFra
import io.reactivex.rxjava3.core.Single


class CarFueFra : PageFra<CalFuelSelect>() {

    override fun initViews() = with(binding) {
        super.initViews()
        with(materialToolbar) {
            title = "车辆加油"
            inflateMenu(R.menu.menu_car_fuel)
        }
    }

    @SuppressLint("CheckResult")
    override fun initBindings() = with(binding) {
        super.initBindings()
        materialToolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.car_fuel_add -> {
                    startAct(CarFuelAddAct::class.java)
                    return@setOnMenuItemClickListener true
                }
                R.id.car_fuel_export_all -> {
                    // 导出全部
                    ExportHelper.exportCarFuels(pageAdapter.data.map { it.carFuel })
                    return@setOnMenuItemClickListener true

                }
                R.id.car_fuel_export_part -> {
                    // 如果没有开启选择模式，则开启选择模式
                    if (!inSelectMode) RxBus.post(ChangeSelectModeEvent(true))
                    else {
                        // 如果开启了，则导出部分
                        val size = pageAdapter.data.filter { it.isSelected }.size
                        if (size == 0) {
                            ToastUtils.showShort("未选中一条记录")
                            return@setOnMenuItemClickListener true
                        }
                        ExportHelper.exportCarFuels(pageAdapter.data.filter { it.isSelected }
                            .map { it.carFuel })
                    }
                    return@setOnMenuItemClickListener true
                }
                else -> return@setOnMenuItemClickListener false
            }
        }
        val menuItem = materialToolbar.menu.findItem(R.id.car_fuel_search)
        val searchView = menuItem.actionView as SearchView
        searchView.queryHint = "输入车牌号"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                RxBus.post(CarFuelSearchEvent(carNo = newText))
                return true
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun initEvents() {
        super.initEvents()
        RxBus.registerEvent(this, CarFuelSearchEvent::class.java, {
            this.carNo = it.carNo
            loadStartPage()
        })
        RxBus.registerEvent(this, CarFuelRefreshEvent::class.java, {
            loadStartPage()
        })
        RxBus.registerEvent(this, ChangeSelectModeEvent::class.java, { event ->
            inSelectMode = event.inSelectMode
            if (!inSelectMode) pageAdapter.data.forEach { it.isSelected = false }
            pageAdapter.notifyDataSetChanged()
        })
    }

    override fun initPageAdapter() {
        pageAdapter = CarFuelAdapter()
    }

    private var carNo = ""

    override val pageSize: Int = 10000

    override fun loadPage(page: Int): Single<PageResponse<CalFuelSelect>> =
        api.getCarFuelList(
            EncryptionRequest.create(
                PageRequest(
                    pageNo = page,
                    pageSize = pageSize,
                    searchParam = CarFuelListParam(carNo = carNo)
                )
            )
        )
            .doOnSuccess { it.items = it.itemsJson.toBeanList() }
            .map { pageResponse ->
                return@map PageResponse(
                    success = pageResponse.success,
                    errorMsg = pageResponse.errorMsg,
                    total = pageResponse.total,
                    items = pageResponse.items!!.map { CalFuelSelect(carFuel = it) },
                    encryptionData = pageResponse.encryptionData
                )
            }

    override val mRecyclerView: RecyclerView
        get() = binding.recyclerView
    override val mSwipeRefreshLayout: SwipeRefreshLayout
        get() = binding.swipeRefreshLayout

//

    private var _binding: UiTitleSwipeBinding? = null

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = UiTitleSwipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}