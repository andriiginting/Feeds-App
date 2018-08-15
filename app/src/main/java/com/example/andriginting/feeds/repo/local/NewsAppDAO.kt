package com.example.andriginting.feeds.repo.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.example.andriginting.feeds.repo.remote.news.NewsArticleData
import com.example.andriginting.feeds.repo.remote.news.NewsResponse

@Dao
interface NewsAppDAO {

    @Query("SELECT * from news_app")
    fun getAllNewsArticle(): List<NewsResponse>

    @Insert(onConflict = REPLACE)
    fun insert(news: NewsResponse)


    @Delete
    fun delet(news: NewsResponse)
}