package com.unicorn.refuel.app.m

import com.google.gson.GsonBuilder
import com.unicorn.refuel.app.transferDateFormat
import org.koin.dsl.module

val appModule = module {

    single {

        GsonBuilder().setDateFormat(transferDateFormat).create()

    }

}