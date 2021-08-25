package com.unicorn.refuel.ui.adapter

import android.content.Intent
import android.view.View
import android.widget.RadioButton
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.unicorn.refuel.R
import com.unicorn.refuel.app.Param
import com.unicorn.refuel.app.inSelectMode
import com.unicorn.refuel.app.safeClicks
import com.unicorn.refuel.app.toDisplayFormat
import com.unicorn.refuel.data.model.CalFuelSelect
import com.unicorn.refuel.data.model.CarFuel
import com.unicorn.refuel.ui.act.base.CarFuelUpdateAct


class CarFuelAdapter : BaseQuickAdapter<CalFuelSelect, BaseViewHolder>(R.layout.item_car_fuel),
    LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: CalFuelSelect) {
        holder.apply {
            with(item.carFuel) {
                val radioButton = holder.getView<RadioButton>(R.id.rbSelect)
                radioButton.visibility = if (inSelectMode) View.VISIBLE else View.GONE

                setText(R.id.tvFuelUpTime, fuelUpTime.toDisplayFormat())
                setText(R.id.tvCarNo, carNo)
                setText(R.id.tvFuelAmount, "${fuelAmount}升")
                setText(R.id.tvPrice, price.toString())
                getView<View>(R.id.root).safeClicks().subscribe {
                    Intent(context, CarFuelUpdateAct::class.java).apply {
                        putExtra(Param, this@with)
                    }.let { context.startActivity(it) }
                }
            }
        }
    }

}