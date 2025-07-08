package com.example.modsen_tasks_roman.domain.usecase

import com.example.modsen_tasks_roman.domain.model.post.ConnectionExceptionDomainModel
import com.example.modsen_tasks_roman.domain.model.postComment.PostCommentDomainModel
import com.example.modsen_tasks_roman.domain.repository.IPostRemoteRepository
import com.example.modsen_tasks_roman.domain.utils.TResult

class GetCommentsByPostIdUseCase(
    private val postRemoteRepository: IPostRemoteRepository
) {
    suspend fun invoke(postId: Int): TResult<List<PostCommentDomainModel>, ConnectionExceptionDomainModel>
    = postRemoteRepository.getCommentsByPostId(postId = postId)
}