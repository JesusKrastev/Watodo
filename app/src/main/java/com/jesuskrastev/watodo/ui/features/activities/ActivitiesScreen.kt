package com.jesuskrastev.watodo.ui.features.activities

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesuskrastev.watodo.ui.composables.shimmerEffect
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
                    imageVector = Icons.Outlined.BookmarkBorder,
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
    onExpand: (ActivityState) -> Unit,
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

@Composable
fun ActivityShimmer(
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .shimmerEffect(),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                fontSize = 18.sp,
                text = "Example",
                color = Color.Transparent,
            )
        }
    }
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
        contentWindowInsets = WindowInsets.statusBars,
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