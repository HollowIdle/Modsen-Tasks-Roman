package com.example.modsen_tasks_roman.ui.features.posts

import SingleFlowEvent
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

    private val _event = SingleFlowEvent<PostsEvent>(viewModelScope)
    val eventFlow = _event.flow


    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            when (val result = getPostsUseCase.invoke()) {
                is TResult.Success -> {
                    val initialPosts = result.data
                    _uiState.update { it.copy(
                        posts = initialPosts,
                        filteredPosts = initialPosts,
                        isLoading = false
                    )}

                    filterPosts( searchFieldText = _uiState.value.searchFieldText)
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

    fun processIntent(intent: PostsIntent){

        when(intent){
            is PostsIntent.PostClicked -> {
                val clickedPost = intent.post
                _event.emit(PostsEvent.NavigateToPostScreen(clickedPost))
            }
            is PostsIntent.SearchFieldTextChanged -> {
                val text = intent.newText

                _uiState.update { it.copy(
                    searchFieldText = text
                ) }

                filterPosts( searchFieldText = text)
            }
        }
    }

    private fun filterPosts(searchFieldText: String){
        val filteredPosts = if(searchFieldText.isBlank()){
            _uiState.value.posts
        }
        else{
            _uiState.value.posts.filter { post ->
                post.title.contains(searchFieldText, ignoreCase = true) ||
                        post.body.contains(searchFieldText, ignoreCase = true)
            }
        }
        _uiState.update { it.copy(filteredPosts = filteredPosts) }
    }

}