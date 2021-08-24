package com.unicorn.refuel.ui.fra.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.unicorn.refuel.app.SimpleComponent


abstract class BaseFra : Fragment(), UI {

    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        initBindings()
        initEvents()
    }

    override fun initViews() {
    }

    override fun initBindings() {
    }

    override fun initEvents() {
    }

    val api by lazy { SimpleComponent().api }

}

