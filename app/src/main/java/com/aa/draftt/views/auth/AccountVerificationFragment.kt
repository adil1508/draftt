package com.aa.draftt.views.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.aa.draftt.databinding.FragmentAccountVerificationBinding

class AccountVerificationFragment : Fragment() {

    private lateinit var binding: FragmentAccountVerificationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountVerificationBinding.inflate(inflater, container, false)
        setupListeners()
        return binding.root
    }

    private fun setupListeners() {
        binding.verifyButton.setOnClickListener {
            findNavController().navigate(AccountVerificationFragmentDirections.actionAccountVerificationFragmentToTeamNameFragment())
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = AccountVerificationFragment()
    }
}