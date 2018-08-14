package com.example.andriginting.feeds.network

import com.example.andriginting.feeds.model.hackernews.HackerNewsResponse
import com.example.andriginting.feeds.model.news.NewsResponse
import io.reactivex.Observable
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
    fun getHackerNewsListId(): Observable<Response<List<Int>>>

    @GET("v0/item/{id}.json")
    fun getHackerNewsItems(@Path("id") itemId: Int): Observable<Response<List<HackerNewsResponse>>>

}