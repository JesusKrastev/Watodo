package com.jesuskrastev.watodo.ui.features.saved

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jesuskrastev.watodo.ui.features.activities.ActivitiesList
import com.jesuskrastev.watodo.ui.features.activities.ActivitiesListShimmer
import com.jesuskrastev.watodo.ui.features.components.TopBar

@Composable
fun NoActivitiesSaved(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Icon(
                modifier = Modifier.size(70.dp),
                imageVector = Icons.Default.BookmarkBorder,
                contentDescription = "No activities saved",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                text = "No hay actividades",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
            )
            Text(
                text = "Guarda tus actividades favoritas aquí.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
fun SavedContent(
    modifier: Modifier = Modifier,
    state: SavedState,
    onEvent: (SavedEvent) -> Unit,
) {
    when {
        state.isLoading -> {
            ActivitiesListShimmer(
                modifier = modifier
            )
        }

        state.activities.isEmpty() -> {
            NoActivitiesSaved(
                modifier = modifier,
            )
        }

        else -> {
            ActivitiesList(
                modifier = modifier,
                expandedActivities = state.expandedActivities,
                activities = state.activities,
                onExpand = {
                    onEvent(SavedEvent.OnExpandActivity(it))
                },
                onSave = { activity ->
                    onEvent(
                        SavedEvent.OnSaveActivity(activity = activity)
                    )
                },
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedTopAppBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
) {
    TopBar(
        modifier = modifier,
        title = "Guardados",
        scrollBehavior = scrollBehavior,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedScreen(
    modifier: Modifier = Modifier,
    state: SavedState,
    onEvent: (SavedEvent) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            SavedTopAppBar()
        },
    ) { paddingValues ->
        SavedContent(
            modifier = Modifier.padding(paddingValues),
            state = state,
            onEvent = onEvent,
        )
    }
}