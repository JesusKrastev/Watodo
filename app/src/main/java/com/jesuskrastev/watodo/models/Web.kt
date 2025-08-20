package com.jesuskrastev.watodo.models

import android.net.Uri

data class Web(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val url : Uri = Uri.EMPTY,
)