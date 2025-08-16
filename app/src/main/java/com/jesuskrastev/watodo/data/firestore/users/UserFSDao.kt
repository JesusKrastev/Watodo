package com.jesuskrastev.watodo.data.firestore.users

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserFSDao @Inject constructor(
    private val firestore: FirebaseFirestore,
) {
    private fun FirebaseFirestore.usersCollection() =
        this.collection("release")
            .document("v1")
            .collection("users")

    suspend fun get(): List<UserFirestore> =
        firestore
            .usersCollection()
            .get()
            .await()
            .documents
            .mapNotNull { document ->
                document.toObject(UserFirestore::class.java)
                    ?.copy(id = document.id)
            }

    suspend fun getById(id: String): UserFirestore? =
        firestore
            .usersCollection()
            .document(id)
            .get()
            .await()
            ?.let { document ->
                document.toObject(UserFirestore::class.java)?.copy(id = document.id)
            }

    suspend fun insert(user: UserFirestore) =
        firestore
            .usersCollection()
            .document(user.id)
            .set(user)
            .await()

    fun update(user: UserFirestore) =
        firestore
            .usersCollection()
            .document(user.id)
            .set(user, SetOptions.merge())
}