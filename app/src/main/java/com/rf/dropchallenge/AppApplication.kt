package com.rf.dropchallenge

import android.app.Application
import com.rf.dropchallenge.di.apiModule
import com.rf.dropchallenge.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@AppApplication)
            modules(listOf(appModule, apiModule))
        }
    }
}