package com.kassaev.notes.pesentation.main_screen.main_screen_detail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.navigation.NavHostController
import com.kassaev.notes.pesentation.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenDetailScaffold(
    navController: NavHostController,
    viewModel: MainViewModel,
    noteId: String?
) {
    val focusRequester = remember { FocusRequester() }

    LifecycleEventEffect(Lifecycle.Event.ON_PAUSE) {
        if(viewModel.exitWithSave){
            viewModel.createOrUpdateNote()
        }
    }

    LaunchedEffect(Unit) {
        if(noteId == null){
            focusRequester.requestFocus()
        }else{
            viewModel.getNoteById(noteId = noteId.toInt())?.let {noteModel ->
                viewModel.textFieldValue = noteModel.text
            }
        }
    }

    BackHandler {
        navController.popBackStack()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "",
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBack,
                            contentDescription = "Back To Main Screen",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.exitWithSave = false
                            viewModel.moveNoteToRecycleBin()
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = "Delete Note",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                )
            )
        }
    ) {paddingValues ->
        TextField(
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Unspecified,
                focusedContainerColor = Color.Unspecified,

                ),
            modifier = Modifier
                .focusRequester(
                    focusRequester = focusRequester
                )
                .fillMaxSize()
                .padding(paddingValues),
            value = viewModel.textFieldValue,
            onValueChange = {
                viewModel.textFieldValue = it
            },
            textStyle = TextStyle.Default.copy(
                fontSize = MaterialTheme.typography.titleLarge.fontSize
            )
        )
    }
}