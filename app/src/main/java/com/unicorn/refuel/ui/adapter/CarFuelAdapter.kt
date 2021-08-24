package com.unicorn.refuel.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.unicorn.refuel.R
import com.unicorn.refuel.app.toDisplayDateFormat
import com.unicorn.refuel.data.model.CarFuel
import com.unicorn.refuel.databinding.ItemCarFuelBinding


class CarFuelAdapter : BaseQuickAdapter<CarFuel, BaseViewHolder>(R.layout.item_car_fuel),
    LoadMoreModule {

    lateinit var binding: ItemCarFuelBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        binding = ItemCarFuelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return super.onCreateViewHolder(parent, viewType)
    }

    override fun convert(holder: BaseViewHolder, item: CarFuel) {
        holder.apply {
            with(item){
                setText(R.id.textView,"${carNo}于${createdServerTime.toDisplayDateFormat()}加油${fuelAmount}升，花费${price}元")
            }
        }
    }

}