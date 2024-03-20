package com.kassaev.notes.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kassaev.notes.pesentation.MainViewModel
import com.kassaev.notes.pesentation.main_screen.MainScreen
import com.kassaev.notes.pesentation.main_screen.main_screen_detail.MainScreenDetail
import com.kassaev.notes.pesentation.main_screen.main_screen_detail.RecycleBinDetailScreen

@Composable
fun Navigation() {
    val navController: NavHostController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.MainScreen.route,
        enterTransition = {
            EnterTransition.None
        },
        exitTransition = {
            ExitTransition.None
        }
    ) {
        composable(
            route = Screen.MainScreen.route
        ) {
            val viewModel: MainViewModel = hiltViewModel()
            MainScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
        composable(
            route = Screen.MainScreenDetail.route + "?noteId={noteId}",
            arguments = listOf(
                navArgument("noteId"){
                    nullable = true
                    defaultValue = null
                }
            )
        ){ backStackEntry ->
            val viewModel: MainViewModel = hiltViewModel()
            MainScreenDetail(
                navController = navController,
                viewModel = viewModel,
                noteId = backStackEntry.arguments?.getString("noteId")
            )
        }
        composable(
            route = Screen.RecycleBinDetailScreen.route + "/{noteId}",
            arguments = listOf(
                navArgument("noteId"){
                    type = NavType.StringType
                }
            )
        ){backStackEntry ->
            val viewModel: MainViewModel = hiltViewModel()
            RecycleBinDetailScreen(
                navController = navController,
                viewModel = viewModel,
                noteId = backStackEntry.arguments?.getString("noteId")
            )
        }
    }
}