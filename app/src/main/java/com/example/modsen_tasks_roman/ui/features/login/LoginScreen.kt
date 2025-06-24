package com.example.modsen_tasks_roman.ui.features.login

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.modsen_tasks_roman.R

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onNavigateToSimplePage: () -> Unit
){

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val eventFlow by remember { mutableStateOf(viewModel.eventFlow)}

    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        eventFlow.collect { event ->
            when(event){
                is LoginEvent.ShowToast -> {
                    Toast.makeText(context,event.message,
                        Toast.LENGTH_LONG).show()
                }
                is LoginEvent.NavigateToSimplePage -> {
                    onNavigateToSimplePage()
                }
            }
        }
    }

    Column(
        Modifier.padding(start = 20.dp, end = 20.dp)
    ) {
        Text(
            text = stringResource(R.string.login_screen_header),
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(top = 40.dp, bottom = 10.dp)
        )
        Row {
            OutlinedTextField(
                value = uiState.usernameInput,
                onValueChange = {newText ->
                    viewModel.processIntent(LoginIntent.UsernameChanged(newText))
                },
                label = { Text(stringResource(R.string.login_screen_login_field_label)) },
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        Row() {
            OutlinedTextField(
                value = uiState.passwordInput,
                onValueChange = {newText ->
                    viewModel.processIntent(LoginIntent.PasswordChanged(newText))
                },
                label = { Text(stringResource(R.string.login_screen_password_field_label)) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 10.dp)
            )
        }
        Row() {
            Button(
                onClick = {
                    viewModel.processIntent(LoginIntent.LoginClicked)
                },
                enabled = uiState.isLoginButtonEnabled,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(bottom = 10.dp)
            ) {
                Text(stringResource(R.string.login_screen_button_text))
            }
        }
    }
}