package com.example.modsen_tasks_roman.ui.navigation

import com.example.modsen_tasks_roman.domain.model.post.PostDomainModel
import com.google.gson.Gson
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

sealed class ScreenRoute(val route: String){

    data object Login : ScreenRoute("login_screen")
    data object TaskSelection: ScreenRoute("task_selection_screen")
    data object SimplePage: ScreenRoute("simple_page_screen")
    data object PostsScreen: ScreenRoute("posts_page_screen")
    data object PostCommentsScreen: ScreenRoute("post_comments_screen/{encodedJson}"){
        fun createRoute(post: PostDomainModel) : String {
            val postJson = Gson().toJson(post)
            val encodedJson = URLEncoder.encode(postJson, StandardCharsets.UTF_8.name())
            return "post_comments_screen/$encodedJson"
        }
    }

}