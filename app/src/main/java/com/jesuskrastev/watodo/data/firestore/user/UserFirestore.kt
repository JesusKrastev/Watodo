package com.jesuskrastev.watodo.data.firestore.user

data class UserFirestore(
    val id: String = "",
    val saved: List<String> = emptyList(),
)