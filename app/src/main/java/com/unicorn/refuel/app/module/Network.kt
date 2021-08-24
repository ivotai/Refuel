package com.unicorn.refuel.app.module

import com.unicorn.refuel.app.baseUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

    single {

        OkHttpClient.Builder()
            .addInterceptor { chain ->
                chain.request().newBuilder()
                    .removeHeader("User-Agent")
                    .addHeader("User-Agent", "SmartKeyManagementApp")
                    .build()
                    .let { chain.proceed(it) }
            }
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

    }

    single {

        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(get())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()

    }

}
