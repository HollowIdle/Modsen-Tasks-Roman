package com.example.modsen_tasks_roman.ui.features.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.modsen_tasks_roman.domain.model.post.PostDomainModel

@Composable
fun Post(
    post: PostDomainModel,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
    ){
        Column {
            HorizontalDivider(
                thickness = 2.dp
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            )  {
                Text(
                    text = post.title,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ) {
                Text(
                    text = post.body,
                    textAlign = TextAlign.Justify
                )
            }
        }
    }


}

@Preview(showBackground = true)
@Composable
private fun PostPreview(){
    Column {
        Text("Много текста")
        Post(
            PostDomainModel(
                1,
                2,
                "Test title 1",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit." +
                        " Donec quis dapibus nisi. Morbi luctus, sem a facilisis imperdiet," +
                        " augue lorem sagittis augue, at suscipit leo felis id metus."
            )
        )
        Text("Мало текста")
        Post(
            PostDomainModel(
                1,
                2,
                "Test title 2",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit."
            )
        )
    }

}