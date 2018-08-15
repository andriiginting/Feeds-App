package com.example.andriginting.feeds.repo.local

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity

@Entity(tableName = "news_app")
data class NewsAppDb(@ColumnInfo(name = "status") var status: String,
                     @ColumnInfo(name = "total") var ntotalResult: Int,
                     @ColumnInfo(name = "source") var articleSource: String,
                     @ColumnInfo(name = "author") var articleAuthor: String,
                     @ColumnInfo(name = "title") var articletitle: String,
                     @ColumnInfo(name = "description") var articleDescription: String,
                     @ColumnInfo(name = "url") var articleUrl: String,
                     @ColumnInfo(name = "urlToImage") var articleImageUrl: String,
                     @ColumnInfo(name = "publishedAt") var articlePublishedAt: String)