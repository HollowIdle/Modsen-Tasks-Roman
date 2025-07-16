package com.example.modsen_tasks_roman.ui.features.posts

import SingleFlowEvent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.modsen_tasks_roman.domain.model.post.ConnectionExceptionDomainModel
import com.example.modsen_tasks_roman.domain.model.post.PostDomainModel
import com.example.modsen_tasks_roman.domain.usecase.ChangeFavoriteStatusUseCase
import com.example.modsen_tasks_roman.domain.usecase.GetPostsUseCase
import com.example.modsen_tasks_roman.domain.usecase.GetSortedAndFilteredPostsUseCase
import com.example.modsen_tasks_roman.domain.utils.TResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PostsViewModel(
    private val getPostsUseCase: GetPostsUseCase,
    private val getSortedAndFilteredPostsUseCase: GetSortedAndFilteredPostsUseCase,
    private val changeFavoriteStatusUseCase: ChangeFavoriteStatusUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(PostsUiState())
    val uiState: StateFlow<PostsUiState> = _uiState.asStateFlow()

    private val _event = SingleFlowEvent<PostsEvent>(viewModelScope)
    val eventFlow = _event.flow

    private val _searchFieldText = MutableStateFlow("")



    init {
        observePosts()

        processIntent(PostsIntent.PostsScreenEntered)
    }

    private fun observePosts(){

        val postsFlow: Flow<TResult<List<PostDomainModel>,ConnectionExceptionDomainModel>> =
            getSortedAndFilteredPostsUseCase.invoke(_searchFieldText)

        viewModelScope.launch {
            postsFlow.collect { result ->
                    _uiState.update { currentState ->
                        when(result){
                            is TResult.Success -> currentState.copy(
                                isLoading = false,
                                filteredAndSortedPosts = result.data,
                                error = null
                            )
                            is TResult.Error -> currentState.copy(
                                isLoading = false,
                                error = result.exception.parseToString()
                            )
                    }
                }
            }
        }
    }

    fun processIntent(intent: PostsIntent){

        when(intent){
            is PostsIntent.PostClicked -> {
                val clickedPost = intent.post
                _event.emit(PostsEvent.NavigateToPostScreen(clickedPost))
            }
            is PostsIntent.SearchFieldTextChanged -> {
                val text = intent.newText

                _searchFieldText.value = text
                _uiState.update { it.copy(
                    searchFieldText = text
                ) }
            }
            is PostsIntent.FavoriteToggleClicked -> {
                viewModelScope.launch {
                    changeFavoriteStatusUseCase.invoke(intent.postId, intent.isCurrentlyFavorite)
                }
            }
            is PostsIntent.PostsScreenEntered -> {
                if (_uiState.value.filteredAndSortedPosts.isEmpty()) {
                    viewModelScope.launch {
                        _uiState.update { it.copy(isLoading = true) }
                        getPostsUseCase.invoke()
                    }
                }
            }
        }
    }


}