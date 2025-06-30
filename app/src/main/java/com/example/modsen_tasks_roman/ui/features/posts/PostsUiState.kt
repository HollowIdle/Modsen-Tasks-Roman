package com.example.modsen_tasks_roman.ui.features.posts

import com.example.modsen_tasks_roman.domain.model.post.PostDomainModel

data class PostsUiState(
    val posts: List<PostDomainModel> = emptyList(),
    val error: Int? = null,
    val isLoading: Boolean = false
)