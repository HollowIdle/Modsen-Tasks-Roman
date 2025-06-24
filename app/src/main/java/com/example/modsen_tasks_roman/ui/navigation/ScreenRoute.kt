package com.example.modsen_tasks_roman.ui.navigation

sealed class ScreenRoute(val route: String){

    data object Login : ScreenRoute("login_screen")
    data object TaskSelection: ScreenRoute("task_selection_screen")
    data object SimplePage: ScreenRoute("simple_page_screen")

}