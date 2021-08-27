package com.unicorn.refuel.ui.adapter

import android.content.Intent
import android.view.View
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.jakewharton.rxbinding4.view.clicks
import com.unicorn.refuel.R
import com.unicorn.refuel.app.*
import com.unicorn.refuel.data.model.CalFuelSelect
import com.unicorn.refuel.ui.act.base.CarFuelUpdateAct


class CarFuelAdapter : BaseQuickAdapter<CalFuelSelect, BaseViewHolder>(R.layout.item_car_fuel),
    LoadMoreModule {

    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int) {
        with(viewHolder) {
            getView<View>(R.id.root).clicks().subscribe {
                if (adapterPosition == RecyclerView.NO_POSITION) return@subscribe
                val item = data[adapterPosition]
                if (inSelectMode) {
                    item.isSelected = !item.isSelected
                    notifyItemChanged(adapterPosition)
                } else {
                    Intent(context, CarFuelUpdateAct::class.java).apply {
                        putExtra(Param, item.carFuel)
                    }.let { context.startActivity(it) }
                }
            }
        }
    }

    override fun convert(holder: BaseViewHolder, item: CalFuelSelect) {
        holder.apply {
            with(item.carFuel) {
                getView<RadioButton>(R.id.rbSelect).visibility =
                    if (inSelectMode) View.VISIBLE else View.GONE
                getView<RadioButton>(R.id.rbSelect).isChecked = item.isSelected
                setText(R.id.tvFuelUpTime, fuelUpTime.toTransferFormat())
                setText(R.id.tvCarNo, carNo)
                setText(R.id.tvFuelAmount, "${fuelAmount}升")
                setText(R.id.tvPrice, price.toString())
            }
        }
    }

}