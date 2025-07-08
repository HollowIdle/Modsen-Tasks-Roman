package com.example.modsen_tasks_roman.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.modsen_tasks_roman.domain.model.post.PostDomainModel
import com.example.modsen_tasks_roman.ui.features.login.LoginScreen
import com.example.modsen_tasks_roman.ui.features.login.LoginViewModel
import com.example.modsen_tasks_roman.ui.features.posts.PostsScreen
import com.example.modsen_tasks_roman.ui.features.posts.PostsViewModel
import com.example.modsen_tasks_roman.ui.features.posts.postComments.PostComments
import com.example.modsen_tasks_roman.ui.features.posts.postComments.PostCommentsViewModel
import com.example.modsen_tasks_roman.ui.features.simplePage.SimplePageScreen
import com.example.modsen_tasks_roman.ui.features.taskSelection.TaskSelectionScreen
import com.google.gson.Gson
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

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
                    navController.navigate(ScreenRoute.PostsScreen.route)
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

        composable(ScreenRoute.PostsScreen.route){
            val postsViewModel: PostsViewModel = koinViewModel()

            PostsScreen(
                postsViewModel,
                onNavigateToPostComments = { post -> navController.navigate(ScreenRoute.PostCommentsScreen.createRoute(post) )
                }

            )
        }

        composable(
            route = ScreenRoute.PostCommentsScreen.route,
            arguments = listOf(navArgument("encodedJson"){
                type = NavType.StringType
            })
        ) {
            backStackEntry ->
            val postJson = backStackEntry.arguments?.getString("encodedJson")
            val decodedJson = URLDecoder.decode(postJson, StandardCharsets.UTF_8.name())

            val post = Gson().fromJson(decodedJson, PostDomainModel::class.java)

            val postCommentsViewModel: PostCommentsViewModel = koinViewModel(
                parameters = { parametersOf(post) }
            )

            PostComments(postCommentsViewModel)
        }

    }
}