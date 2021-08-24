package com.unicorn.refuel.app

import android.content.Context
import com.google.gson.Gson
import com.unicorn.refuel.data.api.SimpleApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SimpleComponent : KoinComponent {

    val api: SimpleApi by inject()

    val context: Context by inject()

    val gson: Gson by inject()

}