package com.unicorn.refuel.ui.fra

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rxjava.rxlife.lifeOnMain
import com.unicorn.refuel.app.*
import com.unicorn.refuel.data.model.base.EncryptionRequest
import com.unicorn.refuel.data.model.base.PageRequest
import com.unicorn.refuel.data.model.param.CarFuelListParam
import com.unicorn.refuel.databinding.FraMainBinding
import com.unicorn.refuel.ui.fra.base.BaseFra

class MainFra : BaseFra() {

    override fun initViews() = with(binding) {

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