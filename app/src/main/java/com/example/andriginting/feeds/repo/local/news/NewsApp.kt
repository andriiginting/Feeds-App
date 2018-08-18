package com.example.andriginting.feeds.repo.local.news

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "news")
data class NewsApp(
        @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Long = 0,
        @ColumnInfo(name = "author") var articleAuthor: String,
        @ColumnInfo(name = "title") var articleTitle: String,
        @ColumnInfo(name = "url") var articleUrl: String,
        @ColumnInfo(name = "image_url") var articleImageUrl: String,
        @ColumnInfo(name = "published_at") var articlePublished: String,
        @ColumnInfo(name = "description") var articleDescription: String)