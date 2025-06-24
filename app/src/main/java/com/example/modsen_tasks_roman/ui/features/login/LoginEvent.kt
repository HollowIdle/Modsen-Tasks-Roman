package com.example.modsen_tasks_roman.ui.features.login

sealed interface LoginEvent {
    data class ShowToast(val message: String) : LoginEvent
    data object NavigateToSimplePage : LoginEvent
}