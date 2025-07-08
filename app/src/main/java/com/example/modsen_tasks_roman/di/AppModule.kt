package com.example.modsen_tasks_roman.di

import com.example.modsen_tasks_roman.data.remote.api.IPostApi
import com.example.modsen_tasks_roman.data.repository.AuthRepositoryImpl
import com.example.modsen_tasks_roman.data.repository.PostRemoteRepositoryImpl
import com.example.modsen_tasks_roman.domain.model.post.PostDomainModel
import com.example.modsen_tasks_roman.domain.repository.IAuthRepository
import com.example.modsen_tasks_roman.domain.repository.IPostRemoteRepository
import com.example.modsen_tasks_roman.domain.usecase.GetCommentsByPostIdUseCase
import com.example.modsen_tasks_roman.domain.usecase.GetPostsUseCase
import com.example.modsen_tasks_roman.domain.usecase.LoginUseCase
import com.example.modsen_tasks_roman.ui.features.login.LoginViewModel
import com.example.modsen_tasks_roman.ui.features.posts.PostsViewModel
import com.example.modsen_tasks_roman.ui.features.posts.postComments.PostCommentsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModules = module {
    single<IAuthRepository> { AuthRepositoryImpl() }
    single<IPostRemoteRepository> { PostRemoteRepositoryImpl(get()) }
    single { IPostApi.create() }
    factory { LoginUseCase(get()) }
    factory { GetPostsUseCase(get()) }
    factory { GetCommentsByPostIdUseCase(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { PostsViewModel(get()) }
    viewModel { (post: PostDomainModel) ->
        PostCommentsViewModel(
            post = post,
            getCommentsByPostIdUseCase = get()
        )
    }
}

