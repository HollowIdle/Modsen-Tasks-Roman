package com.example.modsen_tasks_roman.ui.features.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.modsen_tasks_roman.domain.model.postComment.PostCommentDomainModel

@Composable
fun Comment(
    comment: PostCommentDomainModel
) {

    Column {
        Column (
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = comment.email
            )
            Text(
                text = comment.name,
                fontWeight = FontWeight.Bold
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = comment.body,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp)
            )
        }
        HorizontalDivider(thickness = 2.dp)
    }

}
@Preview(showBackground = true)
@Composable
fun CommentPreview() {
    Column {
        Comment(PostCommentDomainModel(
            1,
            1,
            "Max Verhov",
            "MaxVerhov@gmail.com",
            "Good Post!!!"
        ))

        Comment(PostCommentDomainModel(
            1,
            1,
            "Max Verhov",
            "MaxVerhov@gmail.com",
            "Good Post Good Post Good Post Good Post Good Post Good Post Good Post Good Post Good Post!!!"
        ))
    }
}