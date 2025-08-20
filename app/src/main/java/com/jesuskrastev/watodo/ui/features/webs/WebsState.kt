package com.jesuskrastev.watodo.ui.features.webs

data class WebsState(
    val isLoading: Boolean = false,
    val webs: List<WebState> = emptyList(),
    val expandedWebs: List<String> = emptyList(),
)
