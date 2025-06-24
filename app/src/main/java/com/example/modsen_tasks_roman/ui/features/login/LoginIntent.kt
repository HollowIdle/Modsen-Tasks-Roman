package com.example.modsen_tasks_roman.ui.features.login

sealed interface LoginIntent {
    data class UsernameChanged(val username: String): LoginIntent
    data class PasswordChanged(val password: String): LoginIntent
    data object LoginClicked: LoginIntent
}