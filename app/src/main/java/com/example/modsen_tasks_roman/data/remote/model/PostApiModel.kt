package com.example.modsen_tasks_roman.data.remote.model

import com.example.modsen_tasks_roman.domain.model.post.PostDomainModel
import com.google.gson.annotations.SerializedName

data class PostApiModel(
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("body")
    val body: String
)

fun PostApiModel.toDomainModel() =
    PostDomainModel(
    userId = userId,
    id = id,
    title = title,
    body = body
)
