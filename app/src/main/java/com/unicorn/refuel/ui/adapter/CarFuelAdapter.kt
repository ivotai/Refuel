package com.unicorn.refuel.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.unicorn.refuel.R
import com.unicorn.refuel.data.model.CarFuel

class CarFuelAdapter : BaseQuickAdapter<CarFuel, BaseViewHolder>(R.layout.item_car_fuel),
    LoadMoreModule {

//    lateinit var binding: ItemCarFuelBinding

    override fun convert(holder: BaseViewHolder, item: CarFuel) {
        holder.apply {
//            binding.textView.text = "hehe"
        }
    }

}