package com.example.modsen_tasks_roman.data.repository

import com.example.modsen_tasks_roman.data.mapper.toConnectionExceptionDomainModel
import com.example.modsen_tasks_roman.data.remote.api.IPostApi
import com.example.modsen_tasks_roman.data.remote.model.toDomainModel
import com.example.modsen_tasks_roman.domain.model.post.ConnectionExceptionDomainModel
import com.example.modsen_tasks_roman.domain.model.post.PostDomainModel
import com.example.modsen_tasks_roman.domain.model.postComment.PostCommentDomainModel
import com.example.modsen_tasks_roman.domain.repository.IPostRemoteRepository
import com.example.modsen_tasks_roman.domain.utils.TResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class PostRemoteRepositoryImpl(
    private val api: IPostApi
) : IPostRemoteRepository {
    override suspend fun getPosts(): TResult<List<PostDomainModel>, ConnectionExceptionDomainModel> =
        withContext(Dispatchers.IO) {
            delay(4000)
            runCatching {
                val result = api.getPosts()
                val mapResult = result.map { it.toDomainModel() }
                TResult.Success<List<PostDomainModel>, ConnectionExceptionDomainModel>(mapResult)
            }.getOrElse {
                TResult.Error(it.toConnectionExceptionDomainModel())
            }
    }

    override suspend fun getCommentsByPostId(postId: Int): TResult<List<PostCommentDomainModel>, ConnectionExceptionDomainModel> =
        withContext(Dispatchers.IO) {
            delay(1000)
            runCatching {
                val result = api.getCommentsByPostId(postId)
                val mapResult = result.map { it.toDomainModel() }
                TResult.Success<List<PostCommentDomainModel>, ConnectionExceptionDomainModel>(
                    mapResult
                )
            }.getOrElse {
                TResult.Error(it.toConnectionExceptionDomainModel())
            }
        }

}