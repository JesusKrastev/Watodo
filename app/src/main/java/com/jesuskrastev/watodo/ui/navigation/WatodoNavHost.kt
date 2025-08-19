package com.jesuskrastev.watodo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.jesuskrastev.watodo.ui.features.activities.ActivitiesViewModel
import com.jesuskrastev.watodo.ui.features.saved.SavedViewModel

@Composable
fun WatodoNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val vmActivities: ActivitiesViewModel = hiltViewModel()
    val vmSaved: SavedViewModel = hiltViewModel()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = ActivitiesRoute,
    ) {
        loginScreen()
        activitiesScreen(
            vm = vmActivities,
            onNavigateTo = { destination ->
                navController.navigate(destination)
            },
        )
        savedScreen(
            vm = vmSaved,
        )
    }
}