package com.jesuskrastev.watodo.data

import com.jesuskrastev.watodo.data.firestore.web.WebFSDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WebRepository @Inject constructor(
    private val webDao: WebFSDao
) {
    suspend fun get() = withContext(Dispatchers.IO) {
        webDao.get().map { snapshot -> snapshot.map { it.toWeb() } }
    }

    suspend fun count() = withContext(Dispatchers.IO) {
        webDao.count()
    }
}