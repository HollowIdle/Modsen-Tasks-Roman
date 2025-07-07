package com.example.modsen_tasks_roman.domain.usecase

import com.example.modsen_tasks_roman.domain.model.post.PostDomainModel
import com.example.modsen_tasks_roman.domain.model.post.PostExceptionDomainModel
import com.example.modsen_tasks_roman.domain.repository.IPostRemoteRepository
import com.example.modsen_tasks_roman.domain.utils.TResult

class GetPostsUseCase (
    private val postRemoteRepository: IPostRemoteRepository
) {
    suspend operator fun invoke() : TResult<List<PostDomainModel>, PostExceptionDomainModel>
    = postRemoteRepository.getPosts()
}