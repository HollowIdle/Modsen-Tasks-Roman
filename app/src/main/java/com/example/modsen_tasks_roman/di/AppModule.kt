package com.example.modsen_tasks_roman.di

import com.example.modsen_tasks_roman.data.repository.AuthRepositoryImpl
import com.example.modsen_tasks_roman.domain.repository.IAuthRepository
import com.example.modsen_tasks_roman.domain.usecase.LoginUseCase
import com.example.modsen_tasks_roman.ui.features.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModules = module {
    single<IAuthRepository> {AuthRepositoryImpl()}
    factory { LoginUseCase(get()) }
    viewModel{LoginViewModel(get())}
}