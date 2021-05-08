package com.aa.draftt.repositories

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QuerySnapshot

interface UserRepository {

    // Write User to Firestore
    suspend fun writeUserToFirestore(name: String, email: String): Task<DocumentReference>

    // This will go into a FireStoreRepo
    suspend fun getUserByNameFirestore(email: String): Task<QuerySnapshot>
}