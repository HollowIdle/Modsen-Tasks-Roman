package com.example.modsen_tasks_roman.di.modules

import com.example.modsen_tasks_roman.data.local.db.AppDatabase
import com.example.modsen_tasks_roman.data.local.db.DatabaseProvider
import com.example.modsen_tasks_roman.data.remote.api.IPostApi
import com.example.modsen_tasks_roman.data.repository.AuthRepositoryImpl
import com.example.modsen_tasks_roman.data.repository.PostRemoteRepositoryImpl
import com.example.modsen_tasks_roman.domain.repository.IAuthRepository
import com.example.modsen_tasks_roman.domain.repository.IPostRemoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dataModule = module {

    single<IAuthRepository> { AuthRepositoryImpl() }
    single<IPostRemoteRepository> {
        PostRemoteRepositoryImpl(
            api = get(),
            favoritePostDao = get(),
            externalScope = get()
        )
    }
    single { IPostApi.create() }
    single { DatabaseProvider.provideDatabase(androidApplication()) }
    single { get<AppDatabase>().favoritePostDao() }
    single { CoroutineScope(SupervisorJob() + Dispatchers.IO) }
}