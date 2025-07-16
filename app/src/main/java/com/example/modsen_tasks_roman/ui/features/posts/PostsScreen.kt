package com.example.modsen_tasks_roman.ui.features.posts

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.modsen_tasks_roman.R
import com.example.modsen_tasks_roman.domain.model.post.PostDomainModel
import com.example.modsen_tasks_roman.ui.features.common.Post
import com.example.modsen_tasks_roman.ui.features.common.SearchField
import com.example.modsen_tasks_roman.ui.features.common.SwipeableRow
import com.example.modsen_tasks_roman.ui.features.loader.Loader


@Composable
fun PostsScreen(
    viewModel: PostsViewModel,
    onNavigateToPostComments: (PostDomainModel) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val eventFlow by remember { mutableStateOf(viewModel.eventFlow) }
    val intent : (PostsIntent) -> Unit by remember {
        mutableStateOf(viewModel::processIntent)
    }

    Content(
        state = uiState,
        intent = intent
    )

    LaunchedEffect(key1 = Unit) {
        eventFlow.collect { event ->
            when(event){
                is PostsEvent.NavigateToPostScreen -> {
                    onNavigateToPostComments(event.post)
                }
            }
        }
    }
}

@Composable
private fun Content(
    state: PostsUiState,
    intent: (PostsIntent) -> Unit
){
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)

        ) {
            Text(
                text = stringResource(R.string.posts_page_header),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
            )
        }

        SearchField(
            value = state.searchFieldText,
            onValueChange =  { newText -> intent(PostsIntent.SearchFieldTextChanged(newText)) },
            hint = stringResource(R.string.search_field_hint),
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 5.dp)
                .fillMaxWidth()
        )

        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
            ) {
                Loader(modifier = Modifier)
            }
        }
        else{
            state.error?.let {
                Text(
                    text = stringResource(it),
                    Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
            LazyColumn {
                items(state.filteredAndSortedPosts) { post ->
                    HorizontalDivider(
                        thickness = 2.dp
                    )

                    SwipeableRow(
                        modifier = Modifier.animateItem(
                            placementSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
                        ),
                        onSwipe = {
                            intent(PostsIntent.FavoriteToggleClicked(post.id, post.isFavorite))
                        }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { intent(PostsIntent.PostClicked(post)) }
                        ) {
                            Post(
                                post = post,
                                isFavorite = post.isFavorite,
                                onFavoriteClick = {
                                    intent(PostsIntent.FavoriteToggleClicked(post.id, post.isFavorite))
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}