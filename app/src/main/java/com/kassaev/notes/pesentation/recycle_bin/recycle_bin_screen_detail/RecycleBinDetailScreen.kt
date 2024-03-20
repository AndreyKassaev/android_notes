package com.kassaev.notes.pesentation.main_screen.main_screen_detail

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.kassaev.notes.pesentation.MainViewModel

@Composable
fun RecycleBinDetailScreen(
    navController: NavHostController,
    viewModel: MainViewModel,
    noteId: String?
) {
    RecycleBinDetailScreenScaffold(
        navController,
        viewModel,
        noteId
    )
}