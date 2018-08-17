package com.example.andriginting.feeds.repo.local.news

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.andriginting.feeds.utils.Const.NEWS_DB_NAME

@Database(entities = [(NewsApp::class)], version = 1)
abstract class NewsDatabase: RoomDatabase() {

    companion object {
        @Volatile
        private var INSTANCE: NewsDatabase? = null

        fun getNewsDatabaseInstance(context: Context): NewsDatabase?{
            synchronized(NewsDatabase::class.java){
                INSTANCE = Room.databaseBuilder(context.applicationContext,
                        NewsDatabase::class.java, NEWS_DB_NAME).build()
            }
            return INSTANCE
        }

        fun destroyNewsIntance(){
            INSTANCE = null
        }
    }
}