package com.example.modsen_tasks_roman.ui.features.posts.postComments

sealed interface PostCommentsIntent {
    data object FavoriteToggleClicked : PostCommentsIntent
}