package com.aa.draftt.auth.repositories

import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FirebaseAuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository {

    override suspend fun login(
        email: String,
        password: String
    ): Task<AuthResult> {
        return withContext(Dispatchers.IO) {
            firebaseAuth.signInWithEmailAndPassword(email, password)
        }
    }

    override suspend fun signup(
        email: String,
        password: String
    ): Task<AuthResult> {
        return withContext(Dispatchers.IO) {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
        }
    }

    override suspend fun writeToFirestore(name: String, email: String): Task<DocumentReference> {

        return withContext(Dispatchers.IO){
            // write to firestore
            val userData = hashMapOf(
                "name" to name,
                "email" to email
            )
            firestore.collection("users").add(userData)
        }
    }

    override suspend fun currentUser(): FirebaseUser? {
        // when we're asked for the current user, we should try to get
        // firebase Auth user
        // If that is not null, go to Firestore and grab the user info
        return withContext(Dispatchers.IO){
            firebaseAuth.currentUser
        }
    }

    // TODO: Add logging when implement this properly
    override suspend fun signout() = withContext(Dispatchers.IO) {
        firebaseAuth.signOut()
    }
}