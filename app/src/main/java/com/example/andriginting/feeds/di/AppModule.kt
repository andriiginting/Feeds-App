package com.example.andriginting.feeds.di

import com.example.andriginting.feeds.utils.network.NetworkManager
import com.example.andriginting.feeds.viewmodel.FeedsViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.applicationContext

class AppModule {

    val appModule = applicationContext {

        viewModel {
            FeedsViewModel(get())
        }

        provide { NetworkManager.isInternetConnected(get()) }
    }

}