package com.example.modsen_tasks_roman.ui.features.posts.postComments

import com.example.modsen_tasks_roman.domain.model.post.PostDomainModel
import com.example.modsen_tasks_roman.domain.model.postComment.PostCommentDomainModel

data class PostCommentsUiState(
    val post: PostDomainModel,
    val comments: List<PostCommentDomainModel> = emptyList(),
    val error: Int? = null,
    val isLoading: Boolean = false
)