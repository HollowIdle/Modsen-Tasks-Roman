package com.example.modsen_tasks_roman.ui.features.posts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.modsen_tasks_roman.R
import com.example.modsen_tasks_roman.ui.features.common.Post
import com.example.modsen_tasks_roman.ui.features.loader.Loader


@Composable
fun PostsScreen(
    viewModel: PostsViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Content(
        state = uiState
    )
}

@Composable
private fun Content(
    state: PostsUiState
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
                items(state.posts) { post ->
                    Post(post = post)
                }
            }
        }
    }
}