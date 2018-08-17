package com.example.andriginting.feeds.repo.local.news

import android.arch.persistence.room.*
import com.example.andriginting.feeds.repo.remote.news.NewsResponse
import io.reactivex.Flowable

@Dao
interface NewsDAO {

    @Query("SELECT * FROM news")
    fun getAllNewsArticle(): Flowable<List<NewsResponse>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNews(promotion: NewsResponse)

    @Delete
    fun deleteNews(news: NewsResponse)

}