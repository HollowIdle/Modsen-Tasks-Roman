package com.example.modsen_tasks_roman.di

import com.example.modsen_tasks_roman.di.modules.dataModule
import com.example.modsen_tasks_roman.di.modules.domainModule
import com.example.modsen_tasks_roman.di.modules.uiModule

val appModules =  listOf(dataModule, domainModule, uiModule)

