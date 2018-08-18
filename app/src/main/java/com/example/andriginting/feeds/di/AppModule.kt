package com.example.andriginting.feeds.di

import android.arch.lifecycle.ViewModel
import com.example.andriginting.feeds.network.NetworkClient
import com.example.andriginting.feeds.network.NewsRoutes
import com.example.andriginting.feeds.viewmodel.FeedsViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.applicationContext
import org.koin.dsl.module.module

class AppModule {

    val appModule = applicationContext {

        single { NetworkClient().getNewsServiceRequest() } bind NewsRoutes::class

        single { NetworkClient().getHackerNewsServiceRequest() }

        factory { FeedsViewModel() } bind ViewModel::class
    }

}