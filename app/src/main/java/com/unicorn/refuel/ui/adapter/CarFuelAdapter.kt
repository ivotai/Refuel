package com.unicorn.refuel.ui.adapter

import android.content.Intent
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.unicorn.refuel.R
import com.unicorn.refuel.app.Param
import com.unicorn.refuel.app.safeClicks
import com.unicorn.refuel.app.toDisplayFormat
import com.unicorn.refuel.data.model.CarFuel
import com.unicorn.refuel.ui.act.base.CarFuelUpdateAct


class CarFuelAdapter : BaseQuickAdapter<CarFuel, BaseViewHolder>(R.layout.item_car_fuel),
    LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: CarFuel) {
        holder.apply {
            with(item) {
                setText(R.id.tvFuelUpTime, fuelUpTime.toDisplayFormat())
                setText(R.id.tvCarNo, carNo)
                setText(R.id.tvFuelAmount, "${fuelAmount}升")
                setText(R.id.tvPrice, price.toString())
                getView<View>(R.id.root).safeClicks().subscribe {
                    Intent(context, CarFuelUpdateAct::class.java).apply {
                        putExtra(Param, item)
                    }.let { context.startActivity(it) }
                }
            }
        }
    }

}