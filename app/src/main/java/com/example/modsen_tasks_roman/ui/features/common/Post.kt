package com.example.modsen_tasks_roman.ui.features.common

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.modsen_tasks_roman.R
import com.example.modsen_tasks_roman.domain.model.post.PostDomainModel

@Composable
fun Post(
    post: PostDomainModel,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val infiniteScaleTransition = rememberInfiniteTransition(label = "favorite_pulse")

    val scale by if(isFavorite) {
        infiniteScaleTransition.animateFloat (
            initialValue = 1.0f,
            targetValue = 1.2f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "favorite_scale_animation"
    )
    } else{
        rememberUpdatedState(1.0f)
    }

    val infiniteColorTransition = rememberInfiniteTransition(label = "favorite_color_transition")
    val animatedColor by infiniteColorTransition.animateColor(
        initialValue = Color(0xFFE91E63),
        targetValue = Color(0xFFFF9800),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "favorite_color_animation"
    )



    Box(
        modifier = modifier
    ){
        Column {
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
            Spacer(
                modifier = Modifier
                    .width(8.dp)
            )

            IconButton(onClick = onFavoriteClick) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = stringResource(R.string.favorite_icon_content_description),
                    tint = if (isFavorite) animatedColor else Color.Gray,
                    modifier = Modifier
                        .scale(scale)
                )
            }


        }
    }


}

/*
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

}*/
