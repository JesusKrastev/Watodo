package com.jesuskrastev.watodo.data.firestore.users

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserFSDao @Inject constructor(
    private val firestore: FirebaseFirestore,
) {
    private fun FirebaseFirestore.usersCollection() =
        this.collection("release")
            .document("v1")
            .collection("users")

    suspend fun getById(id: String): UserFirestore? =
        firestore
            .usersCollection()
            .document(id)
            .get()
            .await()
            ?.let { document ->
                document.toObject(UserFirestore::class.java)?.copy(id = document.id)
            }

    fun getByIdFlow(id: String): Flow<UserFirestore?> {
        return firestore
            .usersCollection()
            .document(id)
            .snapshots()
            .map { document ->
                document.toObject(UserFirestore::class.java)?.copy(id = document.id)
            }
    }

    fun insert(user: UserFirestore) =
        firestore
            .usersCollection()
            .document(user.id)
            .set(user)

    fun update(user: UserFirestore) =
        firestore
            .usersCollection()
            .document(user.id)
            .set(user, SetOptions.merge())
}