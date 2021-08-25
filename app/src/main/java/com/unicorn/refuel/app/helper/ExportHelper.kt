package com.unicorn.refuel.app.helper

import com.unicorn.refuel.app.RxBus
import com.unicorn.refuel.app.SimpleComponent
import com.unicorn.refuel.app.toDisplayFormat
import com.unicorn.refuel.app.toast
import com.unicorn.refuel.data.event.ChangeSelectModeEvent
import com.unicorn.refuel.data.model.CarFuel
import jxl.Workbook
import jxl.write.DateTime
import jxl.write.Label
import jxl.write.Number
import java.io.File

object ExportHelper {

    private val headers = listOf(
        "车牌号", "加油时间", "油卡卡号", "油品代码", "单价", "加油升数", "总金额", "加油人姓名"
    )

    fun exportCarFuels(carFuels: List<CarFuel>) {
        val dir = File(SimpleComponent().context.filesDir, "excel")
        dir.mkdir()
        val file = File(dir, "车辆加油记录.xls")
        val workbook = Workbook.createWorkbook(file)
        val sheet = workbook.createSheet("车辆加油记录", 0)

        headers.forEachIndexed { index, it -> sheet.addCell(Label(index, 0, it)) }

        carFuels.forEachIndexed { index, it ->
            sheet.addCell( Label(0, index+1, it.carNo))
            sheet.addCell( DateTime(1, index+1, it.fuelUpTime))
            sheet.addCell( Label(2, index+1, it.fuelCardNo))
            sheet.addCell( Label(3, index+1, it.fuelLabelNumber))
            sheet.addCell( Number(4, index+1, it.unitPrice))
            sheet.addCell( Number(5, index+1, it.fuelAmount))
            sheet.addCell( Number(6, index+1, it.price))
            sheet.addCell( Label(7, index+1, it.userName))
        }

        workbook.write()
        workbook.close()

        "成功导出${carFuels.size}记录".toast()
        RxBus.post(ChangeSelectModeEvent(false))
    }


}