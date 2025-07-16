package com.example.modsen_tasks_roman.di.modules

import com.example.modsen_tasks_roman.domain.model.post.PostDomainModel
import com.example.modsen_tasks_roman.ui.features.login.LoginViewModel
import com.example.modsen_tasks_roman.ui.features.posts.PostsViewModel
import com.example.modsen_tasks_roman.ui.features.posts.postComments.PostCommentsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { PostsViewModel(
        getPostsUseCase = get(),
        changeFavoriteStatusUseCase = get(),
        getSortedAndFilteredPostsUseCase = get()
    ) }
    viewModel { (post: PostDomainModel) ->
        PostCommentsViewModel(
            post = post,
            postRepository = get(),
            getCommentsByPostIdUseCase = get(),
            changeFavoriteStatusUseCase = get()
        )
    }
}