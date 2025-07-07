package com.example.modsen_tasks_roman.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.modsen_tasks_roman.ui.features.login.LoginScreen
import com.example.modsen_tasks_roman.ui.features.login.LoginViewModel
import com.example.modsen_tasks_roman.ui.features.posts.PostsScreen
import com.example.modsen_tasks_roman.ui.features.posts.PostsViewModel
import com.example.modsen_tasks_roman.ui.features.simplePage.SimplePageScreen
import com.example.modsen_tasks_roman.ui.features.taskSelection.TaskSelectionScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
){

    NavHost(
        navController = navController,
        startDestination = ScreenRoute.TaskSelection.route
    ){
        composable(ScreenRoute.TaskSelection.route) {
            TaskSelectionScreen(
                onNavigateToFirstTask = {
                    navController.navigate(ScreenRoute.Login.route)
                },
                onNavigateToSecondTask = {
                    navController.navigate(ScreenRoute.PostsPage.route)
                }
            )
        }

        composable(ScreenRoute.Login.route){

            val loginViewModel: LoginViewModel = koinViewModel()

            LoginScreen(
                viewModel = loginViewModel,
                onNavigateToSimplePage = {
                    navController.navigate(ScreenRoute.SimplePage.route)
                }
            )
        }

        composable(ScreenRoute.SimplePage.route){
            SimplePageScreen()
        }

        composable(ScreenRoute.PostsPage.route){

            val postsViewModel: PostsViewModel = koinViewModel()

            PostsScreen(postsViewModel)
        }

    }
}