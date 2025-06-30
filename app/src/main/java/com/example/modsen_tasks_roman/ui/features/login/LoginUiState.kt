package com.example.modsen_tasks_roman.ui.features.login

data class LoginUiState(
    val usernameInput: String = "",
    val passwordInput: String = "",
    val isLoginButtonEnabled: Boolean = false,
    val isLoading: Boolean = false
)