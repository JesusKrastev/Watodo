package com.jesuskrastev.watodo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun WatodoNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = ActivitiesRoute,
    ) {
        loginScreen(
            onNavigateTo = { destination ->
                navController.navigate(destination)
            },
        )
        activitiesScreen(
            onNavigateTo = { destination ->
                navController.navigate(destination)
            },
        )
        savedScreen()
    }
}