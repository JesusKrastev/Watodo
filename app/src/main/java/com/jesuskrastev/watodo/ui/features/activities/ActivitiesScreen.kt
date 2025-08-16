package com.jesuskrastev.watodo.ui.features.activities

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jesuskrastev.watodo.ui.features.components.Activity
import com.jesuskrastev.watodo.ui.features.components.ActivityShimmer
import com.jesuskrastev.watodo.ui.features.components.TopBar
import com.jesuskrastev.watodo.ui.navigation.Destination
import com.jesuskrastev.watodo.ui.navigation.LoginRoute


@Composable
fun ActivitiesList(
    modifier: Modifier = Modifier,
    expandedActivities: List<String>,
    activities: List<ActivityState>,
    onExpand: (ActivityState) -> Unit,
    onSave: (ActivityState) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        itemsIndexed(activities) { index, activity ->
            Activity(
                activityState = activity,
                expanded = expandedActivities.contains(activity.id),
                onClick = { onExpand(activity) },
                onSave = { onSave(activity) },
            )
        }
    }
}

@Composable
fun ActivitiesListShimmer(
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(20) {
            ActivityShimmer()
        }
    }
}

@Composable
fun ActivitiesContent(
    modifier: Modifier = Modifier,
    onNavigateTo: (Destination) -> Unit,
    state: ActivitiesState,
    onEvent: (ActivitiesEvent) -> Unit,
) {
    when {
        state.isLoading -> {
            ActivitiesListShimmer(
                modifier = modifier
            )
        }

        else -> {
            ActivitiesList(
                modifier = modifier,
                expandedActivities = state.expandedActivities,
                activities = state.activities,
                onExpand = {
                    onEvent(ActivitiesEvent.OnExpandActivity(it))
                },
                onSave = { activity ->
                    onEvent(
                        ActivitiesEvent.OnSaveActivity(
                            activity = activity,
                            onNavigateToLogin = { onNavigateTo(LoginRoute) },
                        )
                    )
                },
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivitiesTopAppBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
) {
    TopBar(
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        title = "Actividades",
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivitiesScreen(
    modifier: Modifier = Modifier,
    state: ActivitiesState,
    onNavigateTo: (Destination) -> Unit,
    onEvent: (ActivitiesEvent) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets.statusBars,
        topBar = {
            ActivitiesTopAppBar()
        },
    ) { paddingValues ->
        ActivitiesContent(
            modifier = Modifier.padding(paddingValues),
            state = state,
            onNavigateTo = onNavigateTo,
            onEvent = onEvent,
        )
    }
}