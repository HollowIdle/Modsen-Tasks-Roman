package com.example.modsen_tasks_roman.ui.features.login

import SingleFlowEvent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.modsen_tasks_roman.domain.model.UserCredentialsDomainModel
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
                        usernameInput = intent.username
                    )
                }
            }
            is LoginIntent.PasswordChanged -> {
                _uiState.update { currentState ->
                    currentState.copy(
                        passwordInput = intent.password
                    )
                }
            }

            is LoginIntent.LoginClicked -> {
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = true
                    )
                }
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
                    _event.emit(LoginEvent.NavigateToSimplePage)
                    _uiState.update { it.copy(isLoading = false) }
                },
                onFailure = {
                    _event.emit(LoginEvent.ShowToast("Ошибка входа"))
                    _uiState.update { it.copy(isLoading = false) }
                }
            )
        }
    }
}
