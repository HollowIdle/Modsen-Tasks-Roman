package com.example.modsen_tasks_roman.domain.model.postComment

data class PostCommentDomainModel(
    val postId: Int,
    val id: Int,
    val name: String,
    val email: String,
    val body: String
)
