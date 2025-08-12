package com.jesuskrastev.watodo.ui.features.activities

sealed interface ActivitiesEvent {
    data class OnExpandActivity(val activity: ActivityState) : ActivitiesEvent
    data object OnLoadMoreActivities: ActivitiesEvent
}