package com.jesuskrastev.watodo.data

import android.util.Log
import com.jesuskrastev.watodo.data.firestore.users.UserFSDao
import com.jesuskrastev.watodo.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserFSDao
) {
    suspend fun get(): List<User> = withContext(Dispatchers.IO) {
        userDao.get().map { it.toUser() }
    }

    suspend fun getById(id: String): User? = withContext(Dispatchers.IO) {
        userDao.getById(id)?.toUser()
    }

    suspend fun insert(user: User) = withContext(Dispatchers.IO) {
        userDao.insert(user.toUserFirestore())
    }

    suspend fun update(user: User) = withContext(Dispatchers.IO) {
        userDao.update(user.toUserFirestore())
    }
}