package com.jesuskrastev.watodo.data.firestore.activity

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ActivityFSDao @Inject constructor(
    private val firestore: FirebaseFirestore,
) {
    suspend fun get(): List<ActivityFirestore> {
        return firestore
            .collection("release")
            .document("v1")
            .collection("activities")
            .get()
            .await()
            .documents
            .mapNotNull { document ->
                document.toObject(ActivityFirestore::class.java)
                    ?.copy(id = document.id)
            }
    }

    suspend fun count(): Int {
        return firestore
            .collection("release")
            .document("v1")
            .collection("activities")
            .get()
            .await()
            .size()
    }
}