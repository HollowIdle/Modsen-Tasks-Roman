package com.example.modsen_tasks_roman.data.local.db.dao

import androidx.room.*
import com.example.modsen_tasks_roman.data.local.db.entity.FavoritePostEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritePostDao {
    @Query("SELECT postId FROM favorite_posts")
    fun getFavoritePostsIdsFlow(): Flow<List<Int>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavorite(favoritePost: FavoritePostEntity)

    @Query("DELETE FROM favorite_posts WHERE postId = :postId")
    suspend fun removeFavorite(postId: Int)
}