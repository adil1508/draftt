package com.aa.draftt.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.aa.draftt.MainActivity
import com.aa.draftt.R
import com.aa.draftt.databinding.FragmentHomeBinding
import com.aa.draftt.viewmodels.HomeViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment @Inject constructor(): Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private lateinit var viewModel: HomeViewModel

    private val firebaseAuth = Firebase.auth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        val sharedPref = requireActivity().getSharedPreferences(
            getString(R.string.SHARED_PREF_FILE_KEY),
            Context.MODE_PRIVATE
        ) ?: null

        val loggedUser = sharedPref?.getString(getString(R.string.SHARED_PREF_USER_EMAIL_KEY), "Could not get user email from shared pref")
        Toast.makeText(requireContext(), loggedUser, Toast.LENGTH_SHORT).show()

        setupListeners()

        return binding.root
    }

    private fun setupListeners() {
        binding.signoutButton.setOnClickListener {
            // Log the user out
            viewModel.signout()
            // Navigate to main activity
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }
    }
}