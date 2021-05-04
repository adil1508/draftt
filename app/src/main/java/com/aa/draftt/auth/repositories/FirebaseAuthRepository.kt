package com.aa.draftt.auth.repositories

import com.aa.draftt.room.dao.UserDao
import com.aa.draftt.room.models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FirebaseAuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val userDao: UserDao
) : AuthRepository {

    /*
    *    In this repo, we need to do two things
    *       - Call the requested function from the ViewModel
    *       - if it's a success, put the UserInfo in the database (on Signup)
    *           or grab user info (on Login)
    *
    *    OR
    *      we can just expose a function that will write user info to the
    *
    *
    *   Question that i failed to ask myself, why must the User be saved in Room?
    *   cus when the app is killed, the in-memory stuff is gone. We need to retrieve user info
    */

    override suspend fun login(
        email: String,
        password: String
    ): Task<com.google.firebase.auth.AuthResult> {
        return withContext(Dispatchers.IO) {
            firebaseAuth.signInWithEmailAndPassword(email, password)
        }
    }

    override suspend fun signup(
        email: String,
        password: String
    ): Task<com.google.firebase.auth.AuthResult> {
        return withContext(Dispatchers.IO) {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
        }
    }

    override suspend fun writeUserToDatabase(
        name: String,
        email: String
    ){
        withContext(Dispatchers.IO){
            userDao.insert(User(name = name, email = email))
        }
    }

    override fun currentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    // TODO: Add logging when implement this properly
    override suspend fun signout() = withContext(Dispatchers.IO) {
        firebaseAuth.signOut()
    }
}