package com.jesuskrastev.watodo.ui.features.webs

import android.net.Uri
import com.jesuskrastev.watodo.models.Web

data class WebState(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val url : Uri = Uri.EMPTY,
)

fun Web.toWebState(): WebState =
    WebState(
        id = id,
        title = title,
        description = description,
        url = url
    )