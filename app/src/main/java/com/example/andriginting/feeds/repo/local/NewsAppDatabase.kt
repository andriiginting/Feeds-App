package com.example.andriginting.feeds.repo.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = [(NewsApp::class)], version = 1)
abstract class NewsAppDatabase : RoomDatabase() {

    abstract fun newsAppDao(): NewsAppDAO

    companion object {
        var dbInstance: NewsAppDatabase? = null

        fun getDbInstance(context: Context): NewsAppDatabase? {
            synchronized(NewsAppDatabase::class) {
                dbInstance = Room.databaseBuilder(context.applicationContext, NewsAppDatabase::class.java,
                        "news_app").build()
            }
            return dbInstance
        }

        fun destroy() {
            dbInstance = null
        }

    }
}
