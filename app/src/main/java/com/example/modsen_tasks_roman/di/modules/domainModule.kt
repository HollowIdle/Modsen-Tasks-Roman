package com.example.modsen_tasks_roman.di.modules

import com.example.modsen_tasks_roman.domain.usecase.ChangeFavoriteStatusUseCase
import com.example.modsen_tasks_roman.domain.usecase.GetCommentsByPostIdUseCase
import com.example.modsen_tasks_roman.domain.usecase.GetPostsUseCase
import com.example.modsen_tasks_roman.domain.usecase.GetSortedAndFilteredPostsUseCase
import com.example.modsen_tasks_roman.domain.usecase.LoginUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { LoginUseCase(get()) }
    factory { GetPostsUseCase(get()) }
    factory { GetCommentsByPostIdUseCase(get()) }
    factory { ChangeFavoriteStatusUseCase(get()) }
    factory { GetSortedAndFilteredPostsUseCase(get()) }

}