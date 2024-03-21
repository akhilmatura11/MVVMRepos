package com.mvvm.repos.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mvvm.repos.data.model.Repos

@Database(entities = [Repos::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun reposDao(): ReposDao

    companion object {
        private lateinit var appDatabase: AppDatabase

        fun getDatabase(context: Context): AppDatabase {
            if (!Companion::appDatabase.isInitialized) {
                synchronized(AppDatabase::class.java) {
                    if (!Companion::appDatabase.isInitialized) {
                        appDatabase = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            "GitHubRepos"
                        ).build()
                    }
                }
            }
            return appDatabase
        }
    }
}
