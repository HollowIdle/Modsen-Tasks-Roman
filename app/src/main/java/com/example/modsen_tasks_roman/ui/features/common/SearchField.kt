package com.example.modsen_tasks_roman.ui.features.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.modsen_tasks_roman.R

@Composable
fun SearchField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint: String = ""
) {

    val handleColor = colorResource(R.color.purple_700)
    val backgroundColor = colorResource(R.color.purple_500)

    val textSelectionColors by remember { mutableStateOf(TextSelectionColors(
        handleColor = handleColor,
        backgroundColor = backgroundColor,
    )) }

    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = modifier
            .defaultMinSize(minHeight = 40.dp)
            .background(
                color = colorResource(R.color.purple_200),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 10.dp)
    ) {

        CompositionLocalProvider(LocalTextSelectionColors provides textSelectionColors) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                cursorBrush = SolidColor(Color.Red),
                singleLine = true,
                textStyle = TextStyle(
                    color = colorResource(R.color.white),
                    fontSize = 16.sp
                ),
                modifier = Modifier
                    .fillMaxWidth(),
            )
        }

        if (value.isEmpty()) {
            Text(
                text = hint,
                fontSize = 16.sp,
                color = colorResource(R.color.white)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SearchFieldPreview(){
    val text by remember { mutableStateOf("ASDASD") }
    SearchField(
        value = text,
        onValueChange = {},
        modifier = Modifier,
        hint = "Enter text"
        )
}