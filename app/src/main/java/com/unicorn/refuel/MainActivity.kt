package com.unicorn.refuel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rxjava.rxlife.lifeOnMain
import com.unicorn.refuel.app.*
import com.unicorn.refuel.data.model.param.CarFuelListParam
import com.unicorn.refuel.data.model.base.PageRequest

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        SimpleComponent().api.getCarFuelList(pageRequest = PageRequest(1,searchParam = CarFuelListParam()))
            .lifeOnMain(this)
            .subscribe(
                { response ->
                    if (response.failed) return@subscribe

                },
                {
                    it.errorMsg().toast()
                }
            )
    }


}