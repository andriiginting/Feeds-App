package com.example.andriginting.feeds.di

import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext

object AppModule {

    fun getModule(): Module = applicationContext {
        bean {  }
    }
}