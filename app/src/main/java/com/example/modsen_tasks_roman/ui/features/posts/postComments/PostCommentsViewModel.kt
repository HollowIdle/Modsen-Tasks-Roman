package com.example.modsen_tasks_roman.ui.features.posts.postComments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.modsen_tasks_roman.domain.model.post.PostDomainModel
import com.example.modsen_tasks_roman.domain.repository.IPostRemoteRepository
import com.example.modsen_tasks_roman.domain.usecase.ChangeFavoriteStatusUseCase
import com.example.modsen_tasks_roman.domain.usecase.GetCommentsByPostIdUseCase
import com.example.modsen_tasks_roman.domain.utils.TResult
import com.example.modsen_tasks_roman.ui.features.posts.parseToString
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PostCommentsViewModel(
    private val post: PostDomainModel,
    private val postRepository: IPostRemoteRepository,
    private val getCommentsByPostIdUseCase: GetCommentsByPostIdUseCase,
    private val changeFavoriteStatusUseCase: ChangeFavoriteStatusUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(PostCommentsUiState(post = post))
    val uiState: StateFlow<PostCommentsUiState> = _uiState.asStateFlow()

    init {
        observePostUpdates()

        loadComments()
    }

    private fun observePostUpdates() {
        viewModelScope.launch {
            postRepository.posts
                .map { result ->
                    if (result is TResult.Success) {
                        result.data.find { it.id == post.id }
                    } else {
                        null
                    }
                }
                .filterNotNull()
                .collect { updatedPost ->
                    _uiState.update { it.copy(post = updatedPost) }
                }
        }
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

    fun processIntent(intent: PostCommentsIntent) {
        when(intent){
            is PostCommentsIntent.FavoriteToggleClicked -> {
                viewModelScope.launch {
                    val currentPost = _uiState.value.post
                    changeFavoriteStatusUseCase.invoke(currentPost.id,currentPost.isFavorite)
                }
            }
        }
    }

}