package com.jesuskrastev.watodo.data.firestore.activity

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ActivityFSDao @Inject constructor(
    private val firestore: FirebaseFirestore,
) {
    private fun FirebaseFirestore.activitiesCollection() =
        this.collection("release")
            .document("v1")
            .collection("activities")

    fun get(): Flow<List<ActivityFirestore>> =
        firestore
            .activitiesCollection()
            .snapshots()
            .map { snapshot ->
                snapshot.documents.mapNotNull { document ->
                    document.toObject(ActivityFirestore::class.java)?.copy(id = document.id)
                }
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