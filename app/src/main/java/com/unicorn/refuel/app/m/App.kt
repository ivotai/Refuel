package com.unicorn.refuel.app.m

import com.google.gson.GsonBuilder
import org.koin.dsl.module

val appModule = module {

    single {

        GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()

    }

}