package com.unicorn.refuel.app

import androidx.multidex.MultiDexApplication
import com.unicorn.refuel.app.module.apiModule
import com.unicorn.refuel.app.module.appModule
import com.unicorn.refuel.app.module.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(appModule, networkModule, apiModule)
        }
    }

}


