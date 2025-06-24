package com.example.modsen_tasks_roman.ui.features.taskSelection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.modsen_tasks_roman.R
import com.example.modsen_tasks_roman.ui.theme.ModsenTasksRomanTheme


@Composable
fun TaskSelectionScreen(
    onNavigateToFirstTask: () -> Unit
){
    Column {
        Text(
            text = stringResource(R.string.task_selection_screen_header),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(top = 40.dp, bottom = 10.dp)
        )
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Button(
                onClick = { onNavigateToFirstTask() }
            ) {
                Text(stringResource(R.string.task_selection_screen_first_task))
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun TaskSelectionScreenPreview(){
    ModsenTasksRomanTheme {
        TaskSelectionScreen(onNavigateToFirstTask = {})
    }
}