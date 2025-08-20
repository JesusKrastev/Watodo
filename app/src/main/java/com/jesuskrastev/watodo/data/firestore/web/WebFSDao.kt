package com.jesuskrastev.watodo.data.firestore.web

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class WebFSDao @Inject constructor(
    private val firestore: FirebaseFirestore,
): ViewModel() {
    private fun FirebaseFirestore.websCollection() =
        this.collection("release")
            .document("v1")
            .collection("webs")

    fun get(): Flow<List<WebFirestore>> =
        firestore
            .websCollection()
            .snapshots()
            .map { snapshot ->
                snapshot.documents.mapNotNull { document ->
                    document.toObject(WebFirestore::class.java)?.copy(id = document.id)
                }
            }

    suspend fun count(): Int =
        firestore
            .websCollection()
            .get()
            .await()
            .size()
}