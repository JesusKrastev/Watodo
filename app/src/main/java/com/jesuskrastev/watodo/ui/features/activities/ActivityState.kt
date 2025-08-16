package com.jesuskrastev.watodo.ui.features.activities

data class ActivityState(
    val id: String,
    val title: String,
    val description: String,
    val isSaved: Boolean = false,
)