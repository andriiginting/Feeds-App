package com.example.andriginting

import android.app.Application
import android.content.Context
import com.example.andriginting.feeds.di.AppModule
import com.example.andriginting.feeds.di.dagger.ApplicationComponent
import com.example.andriginting.feeds.di.dagger.DaggerApplicationComponent
import org.koin.android.ext.android.startKoin


class FeedsApp : Application() {

   private var appsComponent: ApplicationComponent? = null
    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(AppModule().appModule))
        appsComponent = DaggerApplicationComponent.create()
    }

    companion object {
        fun getApplicationComponent(context: Context): ApplicationComponent?{
            return (context.applicationContext as FeedsApp).appsComponent
        }
    }
}