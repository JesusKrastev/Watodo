package com.jesuskrastev.watodo.ui.features.activities

import com.jesuskrastev.watodo.models.Activity

data class ActivityState(
    val id: String,
    val title: String,
    val description: String,
)

fun Activity.toActivityState(): ActivityState =
    ActivityState(
        id = id,
        title = title,
        description = description,
    )