package com.aa.draftt.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.aa.draftt.RedHypergiantActivity
import com.aa.draftt.R
import com.aa.draftt.dataStore
import com.aa.draftt.databinding.FragmentHomeBinding
import com.aa.draftt.viewmodels.HomeViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment @Inject constructor(): Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private lateinit var viewModel: HomeViewModel

    private val firebaseAuth = Firebase.auth

    private var loggedUser: String? = null
    private var userName: String? = null
    private var userID: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        val userEmailKey = stringPreferencesKey(getString(R.string.SHARED_PREF_USER_EMAIL_KEY))
        val userNameKey = stringPreferencesKey(getString(R.string.SHARED_PREF_USER_NAME_KEY))
        val userIdString = stringPreferencesKey(getString(R.string.SHARED_PREF_USER_ID_KEY))

        runBlocking {
            with(requireContext().dataStore.data.first()){
                loggedUser = this[userEmailKey]
                userName = this[userNameKey]
                userID = this[userIdString]
            }
        }


//        loggedUser = sharedPref?.getString(getString(R.string.SHARED_PREF_USER_EMAIL_KEY), "Could not get user email from shared pref")
//        userName = sharedPref?.getString(getString(R.string.SHARED_PREF_USER_NAME_KEY), "Could not get user name from shared pref")
//        userID = sharedPref?.getString(getString(R.string.SHARED_PREF_USER_ID_KEY), "Could not get user id from shared pref")
        Toast.makeText(requireContext(), "$loggedUser, $userName, $userID", Toast.LENGTH_SHORT).show()

        setupListeners()

        return binding.root
    }

    private fun setupListeners() {
        binding.signoutButton.setOnClickListener {
            // Log the user out
            viewModel.signout()
            // Navigate to main activity
            val intent = Intent(requireContext(), RedHypergiantActivity::class.java)
            startActivity(intent)
        }
    }
}