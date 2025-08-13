package com.jesuskrastev.watodo.ui.features.activities

data class ActivitiesState(
    val activities: List<ActivityState> = emptyList(),
    val expandedActivities: List<String> = emptyList(),
    val isLoading: Boolean = true,
)
