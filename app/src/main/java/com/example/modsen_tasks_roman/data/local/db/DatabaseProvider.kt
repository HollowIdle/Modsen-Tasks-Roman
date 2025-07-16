package com.example.modsen_tasks_roman.data.local.db

import android.content.Context
import androidx.room.Room

object DatabaseProvider {

    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "posts_app_database"
        ).build()
    }

}