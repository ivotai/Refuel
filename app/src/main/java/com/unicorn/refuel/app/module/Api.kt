package com.unicorn.refuel.app.module

import com.unicorn.refuel.data.api.SimpleApi
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {

    single {

        get<Retrofit>().create(SimpleApi::class.java)

    }

}