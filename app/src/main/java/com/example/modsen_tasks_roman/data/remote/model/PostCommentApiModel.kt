package com.example.modsen_tasks_roman.data.remote.model

import com.example.modsen_tasks_roman.domain.model.postComment.PostCommentDomainModel
import com.google.gson.annotations.SerializedName

data class PostCommentApiModel(
    @SerializedName("postId")
    val postId: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("body")
    val body: String,
)

fun PostCommentApiModel.toDomainModel() =
    PostCommentDomainModel(
        postId = postId,
        id = id,
        name = name,
        email = email,
        body = body
    )