package com.example.modsen_tasks_roman.ui.features.loader

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Loader(
    modifier: Modifier,
    backgroundColor: Color = Color.LightGray,
    lineColor: Color = Color.Green,
    strokeWidth: Dp = 4.dp
) {



    val infiniteTransition = rememberInfiniteTransition(label = "running_line")

    val lineStart by infiniteTransition.animateFloat(
        initialValue = -0.2f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 800,
                easing = LinearEasing
            )
        ),
        label = "line_start_animation"
    )

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(strokeWidth)
    ) {
        val canvasWidth: Float = size.width
        val canvasHeight: Float = size.height
        val yOffset: Float = canvasHeight / 2

        drawLine(
            color = backgroundColor,
            start = Offset(x = 0f,y = yOffset),
            end = Offset(x = canvasWidth, y = yOffset),
            strokeWidth = strokeWidth.toPx()
        )

        val runningLineWidth: Float = (canvasWidth * 0.2).toFloat()

        val startX: Float = canvasWidth * lineStart
        val endX: Float = startX + runningLineWidth

        drawLine(
            color = lineColor,
            start = Offset(x = startX, y = yOffset),
            end = Offset(x = endX, y = yOffset),
            strokeWidth = strokeWidth.toPx()
        )

    }
}

@Preview(showBackground = true, widthDp = 300)
@Composable
fun LoaderPreview(){
    Loader(modifier = Modifier)
}