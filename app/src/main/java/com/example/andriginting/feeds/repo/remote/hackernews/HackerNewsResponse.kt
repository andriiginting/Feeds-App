package com.example.andriginting.feeds.repo.remote.hackernews

import com.google.gson.annotations.SerializedName

class HackerNewsResponse {
    @SerializedName("by")
    val newsOwner : String? = null

    @SerializedName("descendants")
    val totalCommentCount : Int? = null

    @SerializedName("id")
    val itemsId : Int? = null

    @SerializedName("kids")
    val listOfCommentId : List<Int>? = null

    @SerializedName("score")
    val storiesScore : Int? = null

    @SerializedName("time")
    val publishedAt : Int? = null //using unix time

    @SerializedName("title")
    val storiesTitle : String? = null

    @SerializedName("type")
    val newsType : String? = null

    @SerializedName("url")
    val newsUrl : String? = null
}