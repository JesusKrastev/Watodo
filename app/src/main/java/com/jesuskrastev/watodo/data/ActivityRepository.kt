package com.jesuskrastev.watodo.data

import com.jesuskrastev.watodo.data.firestore.activity.ActivityFSDao
import com.jesuskrastev.watodo.data.room.activity.ActivityDao
import com.jesuskrastev.watodo.models.Activity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ActivityRepository @Inject constructor(
    private val activityFsDao: ActivityFSDao,
    private val activityDao: ActivityDao,
) {
    suspend fun get(): Flow<List<Activity>> = withContext(Dispatchers.IO) {
        val localFlow = activityDao.get().map { it.map { it.toActivity() } }
        val remoteFlow = activityFsDao.get().map { it.map { it.toActivity() } }

        combine(localFlow, remoteFlow) { localList, remoteList ->
            when {
                localList.isEmpty() -> {
                    activityDao.deleteAll()
                    remoteList.forEach { activityDao.insert(it.toActivityEntity()) }
                    remoteList
                }

                localList.size != remoteList.size -> {
                    activityDao.deleteAll()
                    remoteList.forEach { activityDao.insert(it.toActivityEntity()) }
                    remoteList
                }

                else -> {
                    localList
                }
            }
        }
    }
}