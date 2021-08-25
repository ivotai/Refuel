package com.unicorn.refuel.ui.fra.carFuel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.unicorn.refuel.app.RxBus
import com.unicorn.refuel.app.toBeanList
import com.unicorn.refuel.data.event.CarFuelRefreshEvent
import com.unicorn.refuel.data.event.CarFuelSearchEvent
import com.unicorn.refuel.data.model.CarFuel
import com.unicorn.refuel.data.model.base.EncryptionRequest
import com.unicorn.refuel.data.model.base.PageRequest
import com.unicorn.refuel.data.model.base.PageResponse
import com.unicorn.refuel.data.model.param.carFuel.CarFuelListParam
import com.unicorn.refuel.databinding.UiTitleSwipeBinding
import com.unicorn.refuel.ui.adapter.CarFuelAdapter
import com.unicorn.refuel.ui.fra.base.PageFra
import io.reactivex.rxjava3.core.Single


class CarFueFra : PageFra<CarFuel>() {

    override fun initViews() = with(binding) {
        super.initViews()
        materialToolbar.title = "车辆加油"
    }

    private fun initBoomMenuButton() {
//        startAct(CarFuelAddAct::class.java)
    }

    override fun initEvents() {
        super.initEvents()
        RxBus.registerEvent(this, CarFuelSearchEvent::class.java, {
            this.carNo = it.carNo
            loadStartPage()
        })
        RxBus.registerEvent(this, CarFuelRefreshEvent::class.java, {
            loadStartPage()
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