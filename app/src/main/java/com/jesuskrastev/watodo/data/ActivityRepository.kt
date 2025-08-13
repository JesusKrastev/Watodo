package com.jesuskrastev.watodo.data

import com.jesuskrastev.watodo.data.firestore.activity.ActivityFSDao
import com.jesuskrastev.watodo.data.room.activity.ActivityDao
import com.jesuskrastev.watodo.models.Activity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ActivityRepository @Inject constructor(
    private val activityFsDao: ActivityFSDao,
    private val activityDao: ActivityDao,
) {
    suspend fun get(): List<Activity> = withContext(Dispatchers.IO) {
        val local = activityDao.get().map { it.toActivity() }
        val remote = activityFsDao.get().map { it.toActivity() }

        when {
            local.isEmpty() -> {
                activityDao.deleteAll()
                remote.forEach { activityDao.insert(it.toActivityEntity()) }
                remote
            }
            local.size != remote.size -> {
                activityDao.deleteAll()
                remote.forEach { activityDao.insert(it.toActivityEntity()) }
                remote
            }
            else -> {
                local
            }
        }
    }

    suspend fun count(): Int = withContext(Dispatchers.IO) {
        activityFsDao.count()
    }
}