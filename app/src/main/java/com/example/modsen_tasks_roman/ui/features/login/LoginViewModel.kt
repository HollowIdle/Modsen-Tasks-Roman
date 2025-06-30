package com.example.modsen_tasks_roman.ui.features.login

import SingleFlowEvent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.modsen_tasks_roman.domain.model.userCredentials.UserCredentialsDomainModel
import com.example.modsen_tasks_roman.domain.usecase.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _event = SingleFlowEvent<LoginEvent>(viewModelScope)
    val eventFlow = _event.flow

    fun processIntent(intent: LoginIntent){
        when(intent){
            is LoginIntent.UsernameChanged -> {
                _uiState.update { currentState ->
                    currentState.copy(
                        usernameInput = intent.username,
                        isLoginButtonEnabled = intent.username.isNotBlank()
                                && currentState.passwordInput.isNotBlank()
                    )
                }
            }
            is LoginIntent.PasswordChanged -> {
                _uiState.update { currentState ->
                    currentState.copy(
                        passwordInput = intent.password,
                        isLoginButtonEnabled = intent.password.isNotBlank()
                                && currentState.usernameInput.isNotBlank()
                    )
                }
            }

            is LoginIntent.LoginClicked -> {
                performLogin()
            }

        }
    }

    private fun performLogin(){

        val username = _uiState.value.usernameInput
        val password = _uiState.value.passwordInput

        if(username.isBlank() || password.isBlank()){
            _event.emit(LoginEvent.ShowToast("Заполните поля логина и пароля"))
            return
        }

        viewModelScope.launch {

            val credentials = UserCredentialsDomainModel(username,password)

            val result = loginUseCase(credentials)

            result.fold(
                onSuccess = {
                    _uiState.update { it.copy(isLoading = false) }
                    _event.emit(LoginEvent.NavigateToSimplePage)
                },
                onFailure = {
                    _uiState.update { it.copy(isLoading = false) }
                    _event.emit(LoginEvent.ShowToast("Ошибка входа"))
                }
            )
        }
    }
}
