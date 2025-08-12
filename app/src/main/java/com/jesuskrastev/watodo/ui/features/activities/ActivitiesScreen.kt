package com.jesuskrastev.watodo.ui.features.activities

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesuskrastev.watodo.ui.features.components.TopBar

@Composable
fun ActivityTitle(
    modifier: Modifier = Modifier,
    title: String,
) {
    Text(
        modifier = modifier,
        fontSize = 18.sp,
        text = title,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
    )
}

@Composable
fun ActivityDescription(
    modifier: Modifier = Modifier,
    description: String,
) {
    Text(
        modifier = modifier,
        style = MaterialTheme.typography.bodyLarge,
        text = description,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
}

@Composable
fun Activity(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    activityState: ActivityState,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                ActivityTitle(
                    title = activityState.title,
                )
                Icon(
                    modifier = Modifier.clickable(onClick = {}),
                    imageVector = Icons.Outlined.Bookmark,
                    contentDescription = "Save",
                )
            }
            if (expanded)
                ActivityDescription(
                    description = activityState.description,
                )
        }
    }
}

@Composable
fun ActivitiesList(
    modifier: Modifier = Modifier,
    expandedActivities: List<String>,
    activities: List<ActivityState>,
    isLoading: Boolean,
    endReached: Boolean,
    onLoadMore: () -> Unit,
    onExpand: (ActivityState) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        itemsIndexed(activities) { index, activity ->
            if(index >= activities.size -1 && !endReached && !isLoading) onLoadMore()
            Activity(
                activityState = activity,
                expanded = expandedActivities.contains(activity.id),
                onClick = { onExpand(activity) },
            )
        }
        item {
            if (isLoading)
                CircularProgressIndicator()
        }
    }
}

@Composable
fun ActivitiesContent(
    modifier: Modifier = Modifier,
    state: ActivitiesState,
    onEvent: (ActivitiesEvent) -> Unit,
) {
    ActivitiesList(
        modifier = modifier,
        expandedActivities = state.expandedActivities,
        activities = state.activities,
        isLoading = state.isLoading,
        endReached = state.endReached,
        onLoadMore = {
            onEvent(ActivitiesEvent.OnLoadMoreActivities)
        },
        onExpand = {
            onEvent(ActivitiesEvent.OnExpandActivity(it))
        },
    )
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
    onEvent: (ActivitiesEvent) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            ActivitiesTopAppBar()
        },
    ) { paddingValues ->
        ActivitiesContent(
            modifier = Modifier.padding(paddingValues),
            state = state,
            onEvent = onEvent,
        )
    }
}