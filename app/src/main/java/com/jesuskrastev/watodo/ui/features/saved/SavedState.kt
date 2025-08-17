package com.jesuskrastev.watodo.ui.features.saved

import com.jesuskrastev.watodo.ui.features.UserState
import com.jesuskrastev.watodo.ui.features.activities.ActivityState

data class SavedState(
    val activities: List<ActivityState> = emptyList(),
    val expandedActivities: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val user: UserState = UserState(),
)
