package com.kassaev.notes.pesentation.recycle_bin

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.kassaev.notes.R
import com.kassaev.notes.navigation.Screen
import com.kassaev.notes.pesentation.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class
)
@Composable
fun RecycleBinScreenScaffold(
    navController: NavController,
    viewModel: MainViewModel,
    drawerState: DrawerState,
    scope: CoroutineScope
) {

    val noteList by viewModel.noteRecycleBinList.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.recycle_bin))
                },
                navigationIcon = {
                    IconButton(
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = MaterialTheme.colorScheme.primary
                        ),
                        onClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Menu,
                            contentDescription = "Menu"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                contentColor = MaterialTheme.colorScheme.primary,
                onClick = {
                    if (noteList.isNotEmpty()){
                        viewModel.openDialogFAB()
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = "Delete All Notes"
                )
            }
        }
    ) { paddingValues ->
        RecycleBinDeleteConfirmDialog(viewModel = viewModel)
        RecycleBinDeleteAllConfirmDialog(viewModel = viewModel)
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            items(noteList){noteEntity ->
                Column(
                    modifier = Modifier
                        .combinedClickable(
                            onClick = {
                                navController.navigate(Screen.RecycleBinDetailScreen.withArg(noteEntity.noteId.toString()))
                            },
                            onLongClick = {
                                viewModel.openDialogLongClick()
                                viewModel.setNoteToDelete(noteEntity.noteId)
                            },
                        )
                ) {
                    Text(
                        modifier = Modifier
                            .padding(8.dp),
                        text = noteEntity.text,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        maxLines = 1,
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                end = 8.dp,
                                bottom = 8.dp
                            ),
                        text = noteEntity.date,
                        textAlign = TextAlign.End,
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize
                    )
                    Divider()
                }
            }
        }        
    }
}

@Composable
fun RecycleBinDeleteConfirmDialog(viewModel: MainViewModel) {

    if (viewModel.openAlertDialogLongClick){
        Dialog(
            onDismissRequest = {
                viewModel.closeDialogLongClick()
            }
        ) {
            Card(
                modifier = Modifier,
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.primary,
                )
            ) {
                Column {
                    Text(
                        text = stringResource(id = R.string.delete_note),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        textAlign = TextAlign.Center,
                        fontSize = MaterialTheme.typography.headlineLarge.fontSize
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 12.dp,
                                end = 12.dp,
                                bottom = 12.dp
                            ),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        TextButton(
                            onClick = {
                                viewModel.closeDialogLongClick()
                            }
                        ) {
                            Text(
                                text = stringResource(id = R.string.delete_cancel),
                                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }
                        TextButton(
                            onClick = {
                                viewModel.noteIdToDelete?.let { viewModel.deleteNote(noteId = it) }
                                viewModel.closeDialogLongClick()
                            }
                        ) {
                            Text(
                                text = stringResource(id = R.string.delete_yes),
                                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RecycleBinDeleteAllConfirmDialog(viewModel: MainViewModel) {

    if (viewModel.openAlertDialogFAB){
        Dialog(
            onDismissRequest = {
                viewModel.closeDialogFAB()
            }
        ) {
            Card(
                modifier = Modifier,
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.primary,
                )
            ) {
                Column {
                    Text(
                        text = stringResource(id = R.string.delete_all_question),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        textAlign = TextAlign.Center,
                        fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                        style = LocalTextStyle.current.copy(
                            lineHeight = 32.sp
                        )
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 12.dp,
                                end = 12.dp,
                                bottom = 12.dp
                            ),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        TextButton(
                            onClick = {
                                viewModel.closeDialogFAB()
                            }
                        ) {
                            Text(
                                text = stringResource(id = R.string.delete_cancel),
                                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }
                        TextButton(
                            onClick = {
                                viewModel.deleteAllNotesFromRecycleBin()
                                viewModel.closeDialogFAB()
                            }
                        ) {
                            Text(
                                text = stringResource(id = R.string.delete_yes),
                                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }
        }
    }
}