package com.example.modsen_tasks_roman.ui.features.posts.postComments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.modsen_tasks_roman.domain.model.post.PostDomainModel
import com.example.modsen_tasks_roman.domain.usecase.GetCommentsByPostIdUseCase
import com.example.modsen_tasks_roman.domain.utils.TResult
import com.example.modsen_tasks_roman.ui.features.posts.parseToString
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PostCommentsViewModel(
    post: PostDomainModel,
    private val getCommentsByPostIdUseCase: GetCommentsByPostIdUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(PostCommentsUiState(post = post))
    val uiState: StateFlow<PostCommentsUiState> = _uiState.asStateFlow()

    init {
        loadComments()
    }

    private fun loadComments(){
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            when(val result = getCommentsByPostIdUseCase.invoke(_uiState.value.post.id) ){
                is TResult.Success -> {
                    _uiState.update { it.copy(
                        comments = result.data,
                        isLoading = false
                    ) }
                }
                is TResult.Error -> {
                    _uiState.update { it.copy(
                        error = result.exception.parseToString(),
                        isLoading = false
                    ) }
                }
            }
        }
    }

}