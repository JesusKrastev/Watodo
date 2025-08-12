package com.jesuskrastev.watodo.data.room.activity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "activities")
data class ActivityEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
)