package com.aa.draftt.auth.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aa.draftt.models.UserModel
import com.aa.draftt.repositories.AuthRepository
import com.aa.draftt.repositories.UserRepository
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestoreException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) :
    ViewModel() {

    // Tell the observing fragment to navigate away
    private val _navigateToAuthenticated = MutableLiveData(false)
    val navigateToAuthenticated: LiveData<Boolean>
        get() = _navigateToAuthenticated

    // Store user info using our model
    private val _user = MutableLiveData(UserModel())
    val user: LiveData<UserModel>
        get() = _user

    // For observing errors
    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    init {
        updateCurrentUser()
    }

    fun login(email: String, password: String) {

        viewModelScope.launch(Dispatchers.IO) {
            // Login using FirebaseAuth
            val loginTask = authRepository.login(email, password)
            try {
                Tasks.await(loginTask)
                Timber.d("Successfully logged in user with email: $email")
            } catch (e: Exception) {
                // Something went wrong logging user in
                Timber.d("Failed to log in user with email: $email")
                _error.postValue(loginTask.exception?.localizedMessage)
                cancel()
            }

            // Get user info for logged in user
            val userDocTask = userRepository.getUserByEmail(email)
            try {
                val userDoc =
                    Tasks.await(userDocTask).documents[0] // this task returns a max of 1 doc
                Timber.d("Successfully got user doc with email: $email")
                _user.value?.id = userDoc.id
                _user.value?.email = userDoc.data?.get("email") as String
                _user.value?.name = userDoc.data?.get("name") as String
            } catch (e: Exception) {
                // Something went wrong getting user info doc
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

        viewModelScope.launch(Dispatchers.IO) {

            // Sign up using FirebaseAuth
            val signupTask = authRepository.signup(email = email, password = password)
            try {
                Tasks.await(signupTask)
                Timber.d("Successfully signed up user with email: $email")
            } catch (e: Exception) {
                // something went wrong signing up user
                Timber.d("Something went wrong signing up user with email: $email")
                _error.postValue(signupTask.exception?.localizedMessage)
                cancel()
            }

            // Store user info in Firestore
            val fireStoreWriteTask = userRepository.writeUserToFirestore(name = name, email = email)
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
                // something went wrong writing user info
                Timber.d("Failed to write user with name: $name, email: $email")
                _error.postValue(fireStoreWriteTask.exception?.localizedMessage)
                cancel()
            }

            // At this point we've signed up and stored user info
            // and we can navigate away :)
            _navigateToAuthenticated.postValue(true)

        }

    }

    // updates the current user live data
    private fun updateCurrentUser() {
        runBlocking(Dispatchers.IO) {
            val firebaseUser = authRepository.getLoggedInUser()

            firebaseUser?.let {
                // This task returns a max of 1 doc
                val userEmail = it.email!!
                val userDocTask = userRepository.getUserByEmail(userEmail)
                try {
                    val docs = Tasks.await(userDocTask).documents
                    val userDoc = docs[0]
                    Timber.d("Successfully got user doc with email: $userEmail")
                    _user.value?.id = userDoc.id
                    _user.value?.email = userDoc.data?.get("email") as String
                    _user.value?.name = userDoc.data?.get("name") as String
                } catch (e: Exception) {
                    Timber.d("Failed to get user doc with email: $userEmail")
                    Timber.d(e)
                    _error.postValue(userDocTask.exception?.localizedMessage)
                    cancel()
                }
            }
        }
    }

}