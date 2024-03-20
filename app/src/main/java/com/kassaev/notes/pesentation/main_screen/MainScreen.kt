package com.kassaev.notes.pesentation.main_screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.kassaev.notes.pesentation.MainViewModel

@Composable
fun MainScreen(
    navController: NavHostController,
    viewModel: MainViewModel
){
    MainScreenNavigationDrawer(
        navController = navController,
        viewModel = viewModel
    )
}