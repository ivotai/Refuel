package com.unicorn.refuel.ui.fra

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.unicorn.refuel.databinding.FraMainBinding
import com.unicorn.refuel.databinding.FraTestBinding
import com.unicorn.refuel.ui.fra.base.BaseFra

class TestFra : BaseFra() {

    //

    private var _binding: FraTestBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FraTestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}