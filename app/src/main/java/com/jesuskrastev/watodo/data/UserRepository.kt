package com.jesuskrastev.watodo.data

import com.jesuskrastev.watodo.data.firestore.users.UserFSDao
import com.jesuskrastev.watodo.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserFSDao
) {
    suspend fun getByIdFlow(id: String): Flow<User?> = withContext(Dispatchers.IO) {
        userDao.getByIdFlow(id)?.map { it?.toUser() } ?: flowOf(null)
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