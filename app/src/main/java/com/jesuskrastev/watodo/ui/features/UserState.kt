package com.jesuskrastev.watodo.ui.features

import com.jesuskrastev.watodo.models.User

data class UserState(
    val id: String = "",
    val saved: List<String> = emptyList(),
)

fun User.toUserState(): UserState =
    UserState(
        id = id,
        saved = saved,
    )

fun UserState.toUser(): User =
    User(
        id = id,
        saved = saved,
    )