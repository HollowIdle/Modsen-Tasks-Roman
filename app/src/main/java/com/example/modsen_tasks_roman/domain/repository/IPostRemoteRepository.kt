package com.example.modsen_tasks_roman.domain.repository

import com.example.modsen_tasks_roman.domain.model.post.PostDomainModel
import com.example.modsen_tasks_roman.domain.model.post.PostExceptionDomainModel
import com.example.modsen_tasks_roman.domain.utils.TResult

interface IPostRemoteRepository {
    suspend fun getPosts(): TResult<List<PostDomainModel>, PostExceptionDomainModel>
}