package com.jesuskrastev.watodo.ui.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jesuskrastev.watodo.ui.features.activities.ActivitiesScreen
import com.jesuskrastev.watodo.ui.features.activities.ActivitiesState
import com.jesuskrastev.watodo.ui.features.activities.ActivitiesViewModel
import kotlinx.serialization.Serializable

@Serializable
object ActivitiesRoute: Destination

fun NavGraphBuilder.activitiesScreen(
    vm: ActivitiesViewModel,
    onNavigateTo: (Destination) -> Unit,
) {
    composable<ActivitiesRoute> {
        val state by vm.state.collectAsStateWithLifecycle(initialValue = ActivitiesState())

        ActivitiesScreen(
            state = state,
            onEvent = vm::onEvent,
            onNavigateTo = onNavigateTo,
        )
    }
}