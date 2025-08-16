package com.jesuskrastev.watodo.ui.features.saved

import com.jesuskrastev.watodo.ui.features.activities.ActivityState

interface SavedEvent {
    data class OnExpandActivity(val activity: ActivityState) : SavedEvent
    data class OnSaveActivity(val activity: ActivityState) : SavedEvent
}