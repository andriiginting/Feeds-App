package com.example.andriginting.feeds.model.news

data class NewsModel(val articleAuthor: String,
                     val articleTitle: String,
                     val articleDescription: String,
                     val articleUrl: String,
                     val articleImageUrl: String,
                     val articlePublished: String,
                     val source: SourceArticleModel)

class SourceArticleModel(val id: String,
                         val name: String)