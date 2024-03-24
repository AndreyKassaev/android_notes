package com.kassaev.notes.pesentation.main_screen

import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kassaev.notes.R
import com.kassaev.notes.pesentation.MainViewModel
import com.kassaev.notes.pesentation.recycle_bin.RecycleBinScreenScaffold
import kotlinx.coroutines.launch

@Composable
fun MainScreenNavigationDrawer(
    navController: NavController,
    viewModel: MainViewModel
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .requiredWidth(220.dp)
            ) {
                NavigationDrawerItem(
                    label = {
                        Text(text = stringResource(id = R.string.app_name))
                    },
                    selected = viewModel.selectedDrawerItem == 0,
                    onClick = {
                        viewModel.selectedDrawerItem = 0
                        scope.launch {
                            drawerState.close()
                        }
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        unselectedTextColor = MaterialTheme.colorScheme.primary,
                    )
                )
                NavigationDrawerItem(
                    label = {
                        Text(text = stringResource(id = R.string.recycle_bin))
                    },
                    selected = viewModel.selectedDrawerItem == 1,
                    onClick = {
                        viewModel.selectedDrawerItem = 1
                        scope.launch {
                            drawerState.close()
                        }
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        unselectedTextColor = MaterialTheme.colorScheme.primary,
                    )
                )
            }
        }

    ) {
        when(viewModel.selectedDrawerItem){
            0 -> MainScreenScaffold(
                    navController,
                    viewModel,
                    drawerState,
                    scope
                )
            1 -> RecycleBinScreenScaffold(
                    navController,
                    viewModel,
                    drawerState,
                    scope
                )
        }
    }
}