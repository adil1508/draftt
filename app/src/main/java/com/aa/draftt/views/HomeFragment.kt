package com.aa.draftt.views

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.aa.draftt.R
import com.aa.draftt.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment @Inject constructor(): Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        val sharedPref = requireActivity().getSharedPreferences(
            getString(R.string.SHARED_PREF_FILE_KEY),
            Context.MODE_PRIVATE
        ) ?: null

        val loggedUser = sharedPref?.getString(getString(R.string.SHARED_PREF_USER_EMAIL_KEY), "Could not get user email from shared pref")
        Toast.makeText(requireContext(), loggedUser, Toast.LENGTH_SHORT).show()

        return binding.root
    }
}