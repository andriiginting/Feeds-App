package com.example.andriginting.feeds.di.dagger

import com.example.andriginting.feeds.network.NetworkClient
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [NetworkClient::class])
interface ApplicationComponent {
}