package com.jesuskrastev.watodo.ui.features.webs

import android.content.Context

sealed interface WebsEvent {
    data class OnExpandWeb(val web: WebState) : WebsEvent
    data class OnOpenWeb(
        val web: WebState,
        val context: Context,
    ) : WebsEvent
}