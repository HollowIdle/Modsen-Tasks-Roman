package com.example.modsen_tasks_roman.domain.repository

import com.example.modsen_tasks_roman.domain.model.post.PostDomainModel
import com.example.modsen_tasks_roman.domain.model.post.ConnectionExceptionDomainModel
import com.example.modsen_tasks_roman.domain.model.postComment.PostCommentDomainModel
import com.example.modsen_tasks_roman.domain.utils.TResult

interface IPostRemoteRepository {
    suspend fun getPosts(): TResult<List<PostDomainModel>, ConnectionExceptionDomainModel>
    suspend fun getCommentsByPostId(postId: Int): TResult<List<PostCommentDomainModel>,ConnectionExceptionDomainModel>
}