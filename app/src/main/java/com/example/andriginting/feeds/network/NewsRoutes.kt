package com.example.andriginting.feeds.network

import com.example.andriginting.feeds.repo.remote.hackernews.HackerNewsResponse
import com.example.andriginting.feeds.repo.remote.news.NewsResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsRoutes {

    //for news api
    @GET("v2/top-headlines")
    fun getListArticle(@Query("country") countryId: String,
                       @Query("category") newsCategory: String)
            : Observable<Response<NewsResponse>>

    @GET("v0/newstories.json")
    fun getHackerNewsListId(): Single<Response<List<Int>>>

    @GET("v0/item/{id}.json")
    fun getHackerNewsItems(@Path("id") itemId: Int)
            : Observable<Response<HackerNewsResponse>>

}