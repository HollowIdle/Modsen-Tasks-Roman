package com.example.modsen_tasks_roman.ui.features.posts

import com.example.modsen_tasks_roman.domain.model.post.PostDomainModel

sealed interface PostsIntent {
    data class PostClicked(val post: PostDomainModel) : PostsIntent
    data class SearchFieldTextChanged(val newText: String) : PostsIntent
}