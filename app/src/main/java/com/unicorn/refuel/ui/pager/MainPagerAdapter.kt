package com.unicorn.refuel.ui.pager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.unicorn.refuel.ui.fra.CarFueFra
import com.unicorn.refuel.ui.fra.TestFra

class MainPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    companion object {
        val titles = listOf("车辆加油", "车辆维保")
    }

    override fun getItemCount() = titles.size

    override fun createFragment(position: Int): Fragment =
        if (position == 0) CarFueFra() else TestFra()

}