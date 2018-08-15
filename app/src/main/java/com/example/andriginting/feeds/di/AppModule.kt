package com.example.andriginting.feeds.di

import com.example.andriginting.feeds.network.NetworkClient
import com.example.andriginting.feeds.viewmodel.FeedsViewModel
import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext

class AppModule {

    val feedModule = applicationContext {
        viewModel {
            FeedsViewModel()
        }
        bean { FeedsViewModel().fetchAllRepo(get(),get()) }

        bean { NetworkClient().getNewsServiceRequest() }
        bean { NetworkClient().getHackerNewsServiceRequest() }
        factory { NetworkClient() }
    }
}