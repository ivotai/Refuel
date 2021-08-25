package com.unicorn.refuel.ui.adapter

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.unicorn.refuel.R
import com.unicorn.refuel.app.RxBus
import com.unicorn.refuel.app.safeClicks
import com.unicorn.refuel.data.event.CarSelectEvent
import com.unicorn.refuel.data.model.Car


class CarAdapter : BaseQuickAdapter<Car, BaseViewHolder>(R.layout.item_car),
    LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: Car) {
        holder.apply {
            with(item) {
                setText(R.id.textView, item.no)
                holder.getView<View>(R.id.root).safeClicks().subscribe {
                    RxBus.post(CarSelectEvent(car = item))
                }
            }
        }
    }

}