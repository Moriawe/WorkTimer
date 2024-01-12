package com.moriawe.worktimer2.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.moriawe.worktimer2.R
import com.moriawe.worktimer2.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerAppBar(
    currentScreen: Screen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        },
        actions = {
            IconButton(
                onClick = { navController.navigate(Screen.Timer.name) }
            ) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Daily Time Card"
                )
            }
            IconButton(
                onClick = { navController.navigate(Screen.TimeSheet.name) }
            ) {
                Icon(
                    imageVector = Icons.Filled.DateRange,
                    contentDescription = "Worktime overview"
                )
            }
        }
    )
}