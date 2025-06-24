package com.example.modsen_tasks_roman.ui.features.login

import SingleFlowEvent

data class LoginUiState(
    val usernameInput: String = "",
    val passwordInput: String = "",
    val isLoginButtonEnabled: Boolean = false,
    val isLoading: Boolean = false
)