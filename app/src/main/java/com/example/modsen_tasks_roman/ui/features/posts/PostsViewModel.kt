package com.example.modsen_tasks_roman.ui.features.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.modsen_tasks_roman.domain.usecase.GetPostsUseCase
import com.example.modsen_tasks_roman.domain.utils.TResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PostsViewModel(
    private val getPostsUseCase: GetPostsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(PostsUiState())
    val uiState: StateFlow<PostsUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            when (val result = getPostsUseCase.invoke()) {
                is TResult.Success -> {
                    _uiState.update { it.copy(
                        posts = result.data,
                        isLoading = false
                    )}
                }

                is TResult.Error -> {
                    _uiState.update { it.copy(
                        error = result.exception.parseToString(),
                        isLoading = false
                    )}
                }
            }
        }
    }
}