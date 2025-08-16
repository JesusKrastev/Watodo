package com.jesuskrastev.watodo.ui.features.activities

sealed interface ActivitiesEvent {
    data class OnExpandActivity(val activity: ActivityState) : ActivitiesEvent
    data class OnSaveActivity(
        val onNavigateToLogin: () -> Unit,
        val activity: ActivityState,
    ) : ActivitiesEvent
}