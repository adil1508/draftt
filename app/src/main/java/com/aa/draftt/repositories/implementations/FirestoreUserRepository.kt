package com.aa.draftt.repositories.implementations

import com.aa.draftt.repositories.UserRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FirestoreUserRepository @Inject constructor(
    private val firestore: FirebaseFirestore
): UserRepository {

    override suspend fun writeUserToFirestore(name: String, email: String): Task<DocumentReference> {

        return withContext(Dispatchers.IO) {
            // write to firestore
            val userData = hashMapOf(
                "name" to name,
                "email" to email
            )
            firestore.collection("users").add(userData)
        }
    }

    override suspend fun getUserByEmail(email: String): Task<QuerySnapshot> {
        return withContext(Dispatchers.IO) {
            // get data from Firestore
            firestore.collection("users").whereEqualTo("email", email).limit(1).get()
        }
    }

}