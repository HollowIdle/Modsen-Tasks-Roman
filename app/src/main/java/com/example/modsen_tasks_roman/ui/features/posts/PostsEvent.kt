package com.example.modsen_tasks_roman.ui.features.posts

import com.example.modsen_tasks_roman.domain.model.post.PostDomainModel

sealed interface PostsEvent {
    data class NavigateToPostScreen(val post: PostDomainModel) : PostsEvent
}