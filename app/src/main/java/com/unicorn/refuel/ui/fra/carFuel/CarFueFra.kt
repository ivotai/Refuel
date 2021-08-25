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
import com.unicorn.refuel.app.inSelectMode
import com.unicorn.refuel.app.startAct
import com.unicorn.refuel.app.toBeanList
import com.unicorn.refuel.data.event.CarFuelRefreshEvent
import com.unicorn.refuel.data.event.CarFuelSearchEvent
import com.unicorn.refuel.data.event.ChangeSelectModeEvent
import com.unicorn.refuel.data.model.CarFuel
import com.unicorn.refuel.data.model.base.EncryptionRequest
import com.unicorn.refuel.data.model.base.PageRequest
import com.unicorn.refuel.data.model.base.PageResponse
import com.unicorn.refuel.data.model.param.carFuel.CarFuelListParam
import com.unicorn.refuel.databinding.UiTitleSwipeBinding
import com.unicorn.refuel.ui.act.CarFuelAddAct
import com.unicorn.refuel.ui.adapter.CarFuelAdapter
import com.unicorn.refuel.ui.fra.base.PageFra
import io.reactivex.rxjava3.core.Single


class CarFueFra : PageFra<CarFuel>() {

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
        materialToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.car_fuel_add -> {
                    startAct(CarFuelAddAct::class.java)
                    true
                }
                R.id.car_fuel_export_all -> {
                    ToastUtils.showShort("成功导出全部记录")
                    true

                }
                R.id.car_fuel_export_part -> {
                    // 如果没有开启选择模式，则开启选择模式
                    if (!inSelectMode) RxBus.post(ChangeSelectModeEvent(true))
                    else {
                        // 如果开启了，则导出部分数据并关闭选择模式
                        ToastUtils.showShort("成功导出选中记录")
                        RxBus.post(ChangeSelectModeEvent(false))
                    }
                    true
                }
                else -> false
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
        RxBus.registerEvent(this, ChangeSelectModeEvent::class.java, {
            inSelectMode = it.inSelectMode
            pageAdapter.notifyDataSetChanged()
        })
    }

    override fun initPageAdapter() {
        pageAdapter = CarFuelAdapter()
    }

    private var carNo = ""

    override val pageSize: Int = 10000

    override fun loadPage(page: Int): Single<PageResponse<CarFuel>> =
        api.getCarFuelList(
            EncryptionRequest.create(
                PageRequest(
                    pageNo = page,
                    pageSize = pageSize,
                    searchParam = CarFuelListParam(carNo = carNo)
                )
            )
        ).doOnSuccess { it.items = it.itemsJson.toBeanList() }

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