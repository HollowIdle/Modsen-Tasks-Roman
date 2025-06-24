package com.example.modsen_tasks_roman.ui.features.login

data class LoginUiState(
    val usernameInput: String = "",
    val passwordInput: String = "",
    val isLoading: Boolean = false,

){
    val isLoginButtonEnabled: Boolean
        get() = usernameInput.isNotBlank() && passwordInput.isNotBlank() && !isLoading
}