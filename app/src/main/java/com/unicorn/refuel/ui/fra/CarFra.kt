package com.unicorn.refuel.ui.fra

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.unicorn.refuel.app.toBeanList
import com.unicorn.refuel.data.model.Car
import com.unicorn.refuel.data.model.base.EncryptionRequest
import com.unicorn.refuel.data.model.base.PageRequest
import com.unicorn.refuel.data.model.base.PageResponse
import com.unicorn.refuel.data.model.param.CarListParam
import com.unicorn.refuel.databinding.FraCarBinding
import com.unicorn.refuel.databinding.UiTitleSwipeBinding
import com.unicorn.refuel.ui.adapter.CarAdapter
import com.unicorn.refuel.ui.fra.base.PageFra
import io.reactivex.rxjava3.core.Single

class CarFra : PageFra<Car>() {

    override fun addItemDecoration() {
        // no item decoration
    }

    override fun initPageAdapter() {
        pageAdapter = CarAdapter()
    }

    override fun loadPage(page: Int): Single<PageResponse<Car>> =
        api.getCarlList(
            EncryptionRequest.create(
                PageRequest(
                    pageNo = page,
                    searchParam = CarListParam()
                )
            )
        ).doOnSuccess { it.items = it.itemsJson.toBeanList() }

    override val mRecyclerView: RecyclerView
        get() = binding.recyclerView
    override val mSwipeRefreshLayout: SwipeRefreshLayout
        get() = binding.swipeRefreshLayout

    //

    private var _binding: FraCarBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FraCarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}