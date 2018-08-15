package com.example.andriginting

import android.app.Application
import com.example.andriginting.feeds.di.AppModule
import org.koin.android.ext.android.startKoin


class FeedsApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(AppModule().feedModule))
    }
}