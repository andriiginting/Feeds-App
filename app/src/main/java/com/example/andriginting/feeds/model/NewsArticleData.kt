package com.example.andriginting.feeds.model

import com.google.gson.annotations.SerializedName

class NewsArticleData {
    @SerializedName("source")
    var articleSource: SourceArticleResponse? = null

    @SerializedName("author")
    var articleAuthor: String? = null

    @SerializedName("title")
    var articletitle: String? = null

    @SerializedName("description")
    var articleDescription: String? = null

    @SerializedName("url")
    var articleUrl: String? = null

    @SerializedName("urlToImage")
    var articleImageUrl: String? = null

    @SerializedName("publishedAt")
    var articlePublishedAt: String? = null
}
