package com.example.andriginting.feeds.network

import com.example.andriginting.feeds.model.NewsResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsRoutes {

    @GET("v2/top-headlines")
    fun getListArticle(@Query("sources") sourcesName: String)
            : Observable<Response<NewsResponse>>
}