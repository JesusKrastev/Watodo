package com.jesuskrastev.watodo.ui.features.activities

data class ActivitiesState(
    val activities: List<ActivityState> = emptyList(),
    val expandedActivities: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val endReached: Boolean = false,
    val page: Int = 0,
)
