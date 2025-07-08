package com.example.modsen_tasks_roman.ui.features.posts.postComments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.modsen_tasks_roman.R
import com.example.modsen_tasks_roman.domain.model.post.PostDomainModel
import com.example.modsen_tasks_roman.domain.model.postComment.PostCommentDomainModel
import com.example.modsen_tasks_roman.ui.features.common.Comment
import com.example.modsen_tasks_roman.ui.features.common.Post
import com.example.modsen_tasks_roman.ui.features.loader.Loader

@Composable
fun PostComments(
    viewModel: PostCommentsViewModel,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Content(
        state = uiState
    )
}

@Composable
private fun Content(
    state: PostCommentsUiState
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp)
    ){
        Column() {
            if(state.isLoading){
                LoaderSection()
            }
            else{
                PostSection(
                    state.post
                )

                CommentsSectionHeader()

                CommentsSection(
                    state.error,
                    state.comments
                )
            }
        }
    }
}

@Composable
fun LoaderSection(){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp)
    ) {
        Loader(modifier = Modifier)
    }
}

@Composable
fun PostSection(
    post: PostDomainModel
){
    Box(
        Modifier
            .background(
                color = colorResource(R.color.purple_200)
            )
    ){
        Post(post)
    }
}

@Composable
fun CommentsSectionHeader(){
    HorizontalDivider(
        modifier = Modifier
            .padding(top = 5.dp),
        thickness = 2.dp
    )
    Text(
        text = stringResource(R.string.comments_section_header),
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp)
    )
    HorizontalDivider(
        modifier = Modifier
            .padding(top = 5.dp),
        thickness = 2.dp
    )
}

@Composable
fun CommentsSection(
    error: Int?,
    comments: List<PostCommentDomainModel>
){
    error?.let {
        Text(
            text = stringResource(it),
            Modifier
                .fillMaxWidth()
                .padding(10.dp),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ){
        LazyColumn {
            items(comments) { comment ->
                Comment(comment)
            }
        }
    }
}
