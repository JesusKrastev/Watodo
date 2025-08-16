package com.jesuskrastev.watodo.models

data class User(
    val id: String = "",
    val saved: List<String> = emptyList(),
)