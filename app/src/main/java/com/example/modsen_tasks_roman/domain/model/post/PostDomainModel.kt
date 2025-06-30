package com.example.modsen_tasks_roman.domain.model.post

data class PostDomainModel(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)
