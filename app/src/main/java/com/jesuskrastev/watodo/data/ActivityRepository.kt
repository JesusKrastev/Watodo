package com.jesuskrastev.watodo.data

import com.jesuskrastev.watodo.data.firestore.activity.ActivityDao
import com.jesuskrastev.watodo.models.Activity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ActivityRepository @Inject constructor(
    private val activityDao: ActivityDao
) {
    suspend fun getWithPagination(page: Int, pageSize: Int): List<Activity> = withContext(Dispatchers.IO) {
        val startingIndex: Int = page * pageSize
        val activities: List<Activity> = activityDao.get().map { it.toActivity() }

        when {
            activities.isEmpty() -> emptyList()
            startingIndex + pageSize <= activities.size -> activities.subList(startingIndex, startingIndex + pageSize)
            startingIndex < activities.size -> activities.subList(startingIndex, activities.size)
            else -> emptyList()
        }
    }
}