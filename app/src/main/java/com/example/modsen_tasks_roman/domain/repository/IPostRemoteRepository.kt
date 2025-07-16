package com.example.modsen_tasks_roman.domain.repository

import com.example.modsen_tasks_roman.domain.model.post.PostDomainModel
import com.example.modsen_tasks_roman.domain.model.post.ConnectionExceptionDomainModel
import com.example.modsen_tasks_roman.domain.model.postComment.PostCommentDomainModel
import com.example.modsen_tasks_roman.domain.utils.TResult
import kotlinx.coroutines.flow.SharedFlow

interface IPostRemoteRepository {

    val posts: SharedFlow<TResult<List<PostDomainModel>, ConnectionExceptionDomainModel>>

    suspend fun getPosts()
    suspend fun getCommentsByPostId(postId: Int): TResult<List<PostCommentDomainModel>,ConnectionExceptionDomainModel>
    suspend fun changeFavoriteStatus(postId: Int, isCurrentlyFavorite: Boolean)
}