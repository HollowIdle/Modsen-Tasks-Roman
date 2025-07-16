package com.example.modsen_tasks_roman.domain.usecase

import com.example.modsen_tasks_roman.domain.repository.IPostRemoteRepository

class ChangeFavoriteStatusUseCase(
    private val postRemoteRepository: IPostRemoteRepository
) {
    suspend fun invoke(postId: Int, isCurrentlyFavorite: Boolean)
            = postRemoteRepository.changeFavoriteStatus(postId, isCurrentlyFavorite)
}