package com.aa.draftt.auth.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aa.draftt.models.AuthResult
import com.aa.draftt.models.UserModel
import com.aa.draftt.auth.repositories.AuthRepository
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestoreException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepository: AuthRepository) :
    ViewModel() {

    // Tell the observing fragment to navigate away
    private val _navigateToAuthenticated = MutableLiveData(false)
    val navigateToAuthenticated: LiveData<Boolean>
        get() = _navigateToAuthenticated

    // Stores results of making login/signup calls
    private val _authResult = MutableLiveData(
        AuthResult(status = null, error = null)
    )
    val authResult: LiveData<AuthResult>
        get() = _authResult

    // Store user info using our model
    private val _user = MutableLiveData(UserModel())
    val user: LiveData<UserModel>
        get() = _user

    // For observing error
    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    // Stores current logged in user
    private val _firebaseUser = MutableLiveData<FirebaseUser>(currentUser())
    val firebaseUser: LiveData<FirebaseUser?>
        get() = _firebaseUser


    fun login(email: String, password: String) {

        /*
        * Similar to Signup, first we attempt a login, then we get some info from fireStore
        * */

        viewModelScope.launch(Dispatchers.IO) {
            val loginTask = authRepository.login(email, password)
            try {
                Tasks.await(loginTask)
                Timber.d("Successfully logged in user with email: $email")
            } catch (e: Exception) {
                Timber.d("Failed to log in user with email: $email")
                _error.postValue(loginTask.exception?.localizedMessage)
                cancel()
            }

            // At this point, we are successfully logged in,
            // we need to get the Firestore record associated with this email
            // NOTE: We are guaranteed only one record in Firestore cus otherwise,
            // this user would've failed signup

            // This task returns a max of 1 doc
            val userDocTask = authRepository.getFromFirestore(email)
            try {
                val userDoc = Tasks.await(userDocTask).documents[0]
                Timber.d("Successfully got user doc with email: $email")
                _user.value?.id = userDoc.id
                _user.value?.email = userDoc.data?.get("email") as String
                _user.value?.name = userDoc.data?.get("name") as String
            } catch (e: Exception) {
                Timber.d("Failed to get user doc with email: $email")
                _error.postValue(userDocTask.exception?.localizedMessage)
                cancel()
            }

            // At this point we've setup our _user variable
            // and we can navigate away :)
            _navigateToAuthenticated.postValue(true)

        }
    }

    fun signup(name: String, email: String, password: String) {

        // We need to do 2 things here
        // firstly, make a call to FirebaseAuth using email and password
        // secondly, make a call to FirebaseFirestore using email and name to create a record

        viewModelScope.launch(Dispatchers.IO) {
            // Try to sign up using FirebaseAuth
            val signupTask = authRepository.signup(email = email, password = password)
            try {
                Tasks.await(signupTask)
                Timber.d("Successfully signed up user with email: $email")
            } catch (e: Exception) {
                Timber.d("Something went wrong signing up user with email: $email")
                _error.postValue(signupTask.exception?.localizedMessage)
                cancel()
            }

            // At this point, user is signed up from FirebaseAuth
            // We need to store user info in FirebaseFirestore
            // Try to sign up using FirebaseAuth
            val fireStoreWriteTask = authRepository.writeToFirestore(name = name, email = email)
            try {
                // This stuff should really be in a firestore repository
                val doc = Tasks.await(fireStoreWriteTask)
                Timber.d("Successfully stored user with name: $name, email: $email")
                Timber.d("Document created with id: ${doc.id}")
                val docData = Tasks.await(doc.get())
                _user.value?.id = docData.id
                _user.value?.name = docData.data?.get("name") as String?
                _user.value?.email = docData.data?.get("email") as String?
            } catch (e: FirebaseFirestoreException) {
                // something went wrong writing user
                Timber.d("Failed to write user with name: $name, email: $email")
                _error.postValue(fireStoreWriteTask.exception?.localizedMessage)
                cancel()
            }

            // At this point we've setup our _user variable
            // and we can navigate away :)
            _navigateToAuthenticated.postValue(true)

        }
    }

    // updates the current user live data
    private fun currentUser(): FirebaseUser? {
        return runBlocking { authRepository.currentUser() }
    }

}