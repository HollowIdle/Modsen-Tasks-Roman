package com.example.modsen_tasks_roman.data.local.db

import androidx.room.*
import com.example.modsen_tasks_roman.data.local.db.dao.FavoritePostDao
import com.example.modsen_tasks_roman.data.local.db.entity.FavoritePostEntity

@Database(entities = [FavoritePostEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun favoritePostDao(): FavoritePostDao
}