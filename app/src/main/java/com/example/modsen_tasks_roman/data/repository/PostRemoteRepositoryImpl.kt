package com.example.modsen_tasks_roman.data.repository

import com.example.modsen_tasks_roman.data.local.db.dao.FavoritePostDao
import com.example.modsen_tasks_roman.data.local.db.entity.FavoritePostEntity
import com.example.modsen_tasks_roman.data.mapper.toConnectionExceptionDomainModel
import com.example.modsen_tasks_roman.data.remote.api.IPostApi
import com.example.modsen_tasks_roman.data.remote.model.toDomainModel
import com.example.modsen_tasks_roman.domain.model.post.ConnectionExceptionDomainModel
import com.example.modsen_tasks_roman.domain.model.post.PostDomainModel
import com.example.modsen_tasks_roman.domain.model.postComment.PostCommentDomainModel
import com.example.modsen_tasks_roman.domain.repository.IPostRemoteRepository
import com.example.modsen_tasks_roman.domain.utils.TResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.withContext

class PostRemoteRepositoryImpl(
    private val api: IPostApi,
    private val favoritePostDao: FavoritePostDao,
    externalScope: CoroutineScope
) : IPostRemoteRepository {

    private val networkResultFlow = MutableStateFlow<TResult<List<PostDomainModel>, ConnectionExceptionDomainModel>?>(null)

    override val posts: SharedFlow<TResult<List<PostDomainModel>, ConnectionExceptionDomainModel>> =
        combine(
            networkResultFlow.filterNotNull(),
            favoritePostDao.getFavoritePostsIdsFlow()
        ){
            networkResult, favoriteIds ->

            when(networkResult){
                is TResult.Success -> {
                    val updatedPosts = networkResult.data.map { post ->
                        post.copy(isFavorite = favoriteIds.contains(post.id))
                    }
                    TResult.Success(updatedPosts)
                }
                is TResult.Error -> {
                    networkResult
                }
            }
        }.shareIn(
            scope = externalScope,
            started = SharingStarted.WhileSubscribed(5000),
            replay = 1
        )


    override suspend fun getPosts() {
        withContext(Dispatchers.IO) {
            delay(1000)
            runCatching {
                api.getPosts().map { it.toDomainModel() }
            }.onSuccess { posts ->
                networkResultFlow.value = TResult.Success(posts)
            }.onFailure { exception ->
                networkResultFlow.value =
                    TResult.Error(exception.toConnectionExceptionDomainModel())
            }
        }
    }

    override suspend fun getCommentsByPostId(postId: Int): TResult<List<PostCommentDomainModel>, ConnectionExceptionDomainModel> =
        withContext(Dispatchers.IO) {
            delay(1000)
            runCatching {
                val result = api.getCommentsByPostId(postId)
                val mapResult = result.map { it.toDomainModel() }
                TResult.Success<List<PostCommentDomainModel>, ConnectionExceptionDomainModel>(
                    mapResult
                )
            }.getOrElse {
                TResult.Error(it.toConnectionExceptionDomainModel())
            }
        }

    override suspend fun changeFavoriteStatus(postId: Int, isCurrentlyFavorite: Boolean) {
        withContext(Dispatchers.IO){
            if(isCurrentlyFavorite){
                favoritePostDao.removeFavorite(postId)
            }
            else{
                favoritePostDao.addFavorite(FavoritePostEntity(postId))
            }
        }
    }

}