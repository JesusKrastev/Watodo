package com.jesuskrastev.watodo.data.firestore.users

data class UserFirestore(
    val id: String = "",
    val saved: List<String> = emptyList(),
)