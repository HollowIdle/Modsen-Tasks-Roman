package com.example.modsen_tasks_roman.domain.usecase

import com.example.modsen_tasks_roman.domain.model.post.ConnectionExceptionDomainModel
import com.example.modsen_tasks_roman.domain.model.post.PostDomainModel
import com.example.modsen_tasks_roman.domain.repository.IPostRemoteRepository
import com.example.modsen_tasks_roman.domain.utils.TResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetSortedAndFilteredPostsUseCase(
    private val postRemoteRepository: IPostRemoteRepository
) {
    fun invoke(searchFieldFlow: Flow<String>) :
            Flow<TResult<List<PostDomainModel>, ConnectionExceptionDomainModel>> {
        return combine(
            postRemoteRepository.posts,
            searchFieldFlow
        ) {
            postsResult, searchFieldText ->
            when(postsResult){
                is TResult.Success -> {
                    val allPosts = postsResult.data
                    val filtered = if (searchFieldText.isBlank()) {
                        allPosts
                    } else {
                        filterPosts(allPosts, searchFieldText)
                    }
                    val sorted = sortPosts(filtered)

                    TResult.Success(sorted)
                }
                is TResult.Error -> {
                    postsResult
                }
            }
        }
    }

    private fun sortPosts(posts: List<PostDomainModel>): List<PostDomainModel> {
        return posts.sortedWith(compareByDescending { it.isFavorite })
    }

    private fun filterPosts(posts: List<PostDomainModel> ,searchFieldText: String): List<PostDomainModel> {
        return if (searchFieldText.isBlank()) {
            posts
        } else {
            posts.filter { post ->
                post.title.contains(searchFieldText, ignoreCase = true) ||
                        post.body.contains(searchFieldText, ignoreCase = true)
            }
        }
    }
}