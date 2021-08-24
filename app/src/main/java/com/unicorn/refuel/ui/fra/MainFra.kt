package com.unicorn.refuel.ui.fra

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.library.fontawesome.FontAwesome
import com.mikepenz.iconics.utils.sizeDp
import com.rxjava.rxlife.lifeOnMain
import com.unicorn.refuel.app.*
import com.unicorn.refuel.data.model.base.EncryptionRequest
import com.unicorn.refuel.data.model.base.PageRequest
import com.unicorn.refuel.data.model.param.CarFuelListParam
import com.unicorn.refuel.databinding.FraMainBinding
import com.unicorn.refuel.ui.fra.base.BaseFra
import com.unicorn.refuel.ui.pager.MainPagerAdapter

class MainFra : BaseFra() {

    override fun initViews() = with(binding) {
        initM()
    }

    private fun initM() = with(binding) {
        viewPager2.adapter = MainPagerAdapter(this@MainFra)
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = MainPagerAdapter.titles[0]
                    tab.icon = IconicsDrawable(requireContext(), FontAwesome.Icon.faw_car_alt).apply {
                        sizeDp = 24
                    }
                }
                1 -> {
                    tab.text = MainPagerAdapter.titles[1]
                    tab.icon = IconicsDrawable(requireContext(), FontAwesome.Icon.faw_tools).apply {
                        sizeDp = 24
                    }
                }
            }
        }.attach()
    }

    override fun initBindings(): Unit = with(binding) {
        getCarFuelList()
    }

    private fun getCarFuelList() {
        api.getCarFuelList(
            EncryptionRequest.create(
                PageRequest(
                    pageNo = 1,
                    searchParam = CarFuelListParam()
                )
            )
        ).doOnSuccess { it.items = it.itemsJson.toBeanList() }.lifeOnMain(this).subscribe(
            { response ->
                if (response.failed) return@subscribe

            },
            {
                it.errorMsg().toast()
            }
        )

    }

    //

    private var _binding: FraMainBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FraMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}