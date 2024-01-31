package com.moriawe.worktimer2.presentation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.moriawe.worktimer2.domain.util.TimeFormatters.dayFormatter
import com.moriawe.worktimer2.navigation.Screen
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerAppBar(
    currentScreen: Screen,
    //canNavigateBack: Boolean,
    //navigateUp: () -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            when (currentScreen) {
                Screen.TimerScreen -> Text(LocalDateTime.now().format(dayFormatter))
                Screen.TimeSheetScreen -> Text(currentScreen.title)
                Screen.SettingsScreen -> Text(currentScreen.title)
                Screen.DialogScreen -> Text(currentScreen.title)
            }
             },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        actions = {
            IconButton(
                onClick = { navController.navigate(Screen.TimerScreen.route) }
            ) {
                Icon(
                    painter = painterResource(id = Screen.TimerScreen.icon),
                    contentDescription = "Daily Time Card"
                )
            }
            IconButton(
                onClick = { navController.navigate(Screen.TimeSheetScreen.route) }
            ) {
                Icon(
                    painter = painterResource(id = Screen.TimeSheetScreen.icon),
                    contentDescription = "Worktime overview"
                )
            }
        }
    )
}

/*
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
 */