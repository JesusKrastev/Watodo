package com.jesuskrastev.watodo.ui.features.activities

import com.jesuskrastev.watodo.ui.features.UserState

data class ActivitiesState(
    val activities: List<ActivityState> = emptyList(),
    val expandedActivities: List<String> = emptyList(),
    val isLoading: Boolean = true,
    val user: UserState = UserState(),
)
