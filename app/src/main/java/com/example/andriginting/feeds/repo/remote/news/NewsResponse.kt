package com.example.andriginting.feeds.repo.remote.news

import com.google.gson.annotations.SerializedName

class NewsResponse {

    @SerializedName("status")
    var statusResponse: String? = null

    @SerializedName("totalResults")
    var resultArticle: Int? = null

    @SerializedName("articles")
    var articleData: List<NewsArticleData>? = null
}