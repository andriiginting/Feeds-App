package com.example.andriginting.feeds.model.news

import com.google.gson.annotations.SerializedName

class SourceArticleResponse{
    @SerializedName("id")
    var sourceId: String? = null

    @SerializedName("name")
    var sourceName: String? = null
}