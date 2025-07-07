package com.example.modsen_tasks_roman.data.repository

import android.util.Log
import com.example.modsen_tasks_roman.data.mapper.toPostExceptionDomainModel
import com.example.modsen_tasks_roman.data.remote.api.IPostApi
import com.example.modsen_tasks_roman.data.remote.model.toDomainModel
import com.example.modsen_tasks_roman.domain.model.post.PostDomainModel
import com.example.modsen_tasks_roman.domain.model.post.PostExceptionDomainModel
import com.example.modsen_tasks_roman.domain.repository.IPostRemoteRepository
import com.example.modsen_tasks_roman.domain.utils.TResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class PostRemoteRepositoryImpl(
    private val api: IPostApi
) : IPostRemoteRepository {
    override suspend fun getPosts(): TResult<List<PostDomainModel>, PostExceptionDomainModel> =
        withContext(Dispatchers.IO) {
            delay(2000)
            runCatching {
                val result = api.getPosts()
                Log.d("getting result",result.toString())
                val mapResult = result.map{ it.toDomainModel() }
                TResult.Success<List<PostDomainModel>, PostExceptionDomainModel>(mapResult)
            }.getOrElse {
                TResult.Error(it.toPostExceptionDomainModel())
            }
    }
}