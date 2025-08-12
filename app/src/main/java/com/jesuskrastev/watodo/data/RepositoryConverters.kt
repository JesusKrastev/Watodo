package com.jesuskrastev.watodo.data

import com.jesuskrastev.watodo.data.firestore.activity.ActivityFirestore
import com.jesuskrastev.watodo.data.room.activity.ActivityEntity
import com.jesuskrastev.watodo.models.Activity

fun ActivityFirestore.toActivity(): Activity =
    Activity(
        id = id,
        title = title,
        description = description,
    )

fun Activity.toActivityEntity(): ActivityEntity =
    ActivityEntity(
        id = id,
        title = title,
        description = description,
    )

fun ActivityEntity.toActivity(): Activity =
    Activity(
        id = id,
        title = title,
        description = description,
    )