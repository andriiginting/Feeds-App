package com.example.andriginting.feeds.network

import com.example.andriginting.feeds.BuildConfig
import com.example.andriginting.feeds.utils.Const.HACKER_NEWS_BASE_URL
import com.example.andriginting.feeds.utils.Const.NEWS_API_BASE_URL
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkClient {

    @Provides
    @Singleton
    fun getNewsServiceRequest(): NewsRoutes = getNewsInstance().create(NewsRoutes::class.java)

    @Provides
    @Singleton
    fun getHackerNewsServiceRequest(): NewsRoutes = getHackerNewsInstance().create(NewsRoutes::class.java)

    private fun getNewsInstance(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(NEWS_API_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(getClient())
                .build()
    }

    private fun getHackerNewsInstance(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(HACKER_NEWS_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(getHackerNewsClient())
                .build()
    }

    private fun getHackerNewsClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor())
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .build()
    }

    private fun getClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor())
                .addInterceptor(defaultHttpClient())
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .build()
    }

    private fun loggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }



    @Throws(IOException::class)
    private fun defaultHttpClient(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
                    .newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("X-Api-Key", BuildConfig.NEWS_API_KEY)
                    .build()

            return@Interceptor chain.proceed(request)
        }
    }
}