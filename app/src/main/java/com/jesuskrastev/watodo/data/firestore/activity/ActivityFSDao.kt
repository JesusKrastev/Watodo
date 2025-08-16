package com.jesuskrastev.watodo.data.firestore.activity

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ActivityFSDao @Inject constructor(
    private val firestore: FirebaseFirestore,
) {
    private fun FirebaseFirestore.activitiesCollection() =
        this.collection("release")
            .document("v1")
            .collection("activities")

    suspend fun get(): List<ActivityFirestore> =
        firestore
            .activitiesCollection()
            .get()
            .await()
            .documents
            .mapNotNull { document ->
                document.toObject(ActivityFirestore::class.java)
                    ?.copy(id = document.id)
            }

    suspend fun getById(id: String): ActivityFirestore? =
        firestore
            .activitiesCollection()
            .document(id)
            .get()
            .await()
            ?.let { document ->
                document.toObject(ActivityFirestore::class.java)?.copy(id = document.id)
            }

    suspend fun count(): Int =
        firestore
            .activitiesCollection()
            .get()
            .await()
            .size()
}