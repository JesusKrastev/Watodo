package com.jesuskrastev.watodo.data

import android.net.Uri
import com.jesuskrastev.watodo.data.firestore.activity.ActivityFirestore
import com.jesuskrastev.watodo.data.firestore.user.UserFirestore
import com.jesuskrastev.watodo.data.firestore.web.WebFirestore
import com.jesuskrastev.watodo.data.room.activity.ActivityEntity
import com.jesuskrastev.watodo.models.Activity
import com.jesuskrastev.watodo.models.User
import com.jesuskrastev.watodo.models.Web

fun WebFirestore.toWeb(): Web =
    Web(
        id = id,
        title = title,
        description = description,
        url = Uri.parse(url),
    )

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

fun UserFirestore.toUser(): User =
    User(
        id = id,
        saved = saved,
    )

fun User.toUserFirestore(): UserFirestore =
    UserFirestore(
        id = id,
        saved = saved,
    )